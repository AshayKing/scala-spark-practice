package io.github.ashayking.spark.sql.ml.customerchurn

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.tuning.CrossValidator
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics

import org.apache.spark._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._

/**
 * @author Ashay S Patil
 *
 */
object CustomerChurnClassification {

  def main(args: Array[String]): Unit = {

    val trainFilePath = "src/main/resources/customerchurn/churn-bigml-80.csv"
    val testFilePath = "src/main/resources/customerchurn/churn-bigml-20.csv"

    // Creating SparkSession
    val spark: SparkSession = SparkSession
      .builder
      .master("local[2]")
      .appName("churn")
      .getOrCreate()

    import spark.implicits._

    // Traning Dataset
    val train: Dataset[Account] = spark
      .read
      .option("inferSchema", "false")
      .schema(Models.schema)
      .csv(trainFilePath)
      .as[Account]

    //train.take(1)
    train.cache
    //println(train.count)

    // Testing Dataset
    val test: Dataset[Account] = spark
      .read
      .option("inferSchema", "false")
      .schema(Models.schema)
      .csv(testFilePath).as[Account]

    //test.take(2)
    test.cache
    //println(test.count)

    // Printing Schema & Some data
    //train.printSchema()
    //train.show

    // Caching table
    train.createOrReplaceTempView("account")
    spark.catalog.cacheTable("account")

    // Printing results with respect to label
    train.groupBy("churn").count.show

    // It is unbalanced dataset so selected only fraction of data
    val fractions = Map("False" -> .17, "True" -> 1.0)
    val strain = train.stat.sampleBy("churn", fractions, 36L)

    strain.groupBy("churn").count.show

    // Removing non-required columns
    val ntrain = strain
      .drop("state")
      .drop("acode")
      .drop("vplan")
      .drop("tdcharge")
      .drop("techarge")
    println(ntrain.count)
    ntrain.show

    // handling non-numeric variables
    val ipindexer = new StringIndexer()
      .setInputCol("intlplan")
      .setOutputCol("iplanIndex")

    val labelindexer = new StringIndexer()
      .setInputCol("churn")
      .setOutputCol("label")

    val featureCols = Array("len", "iplanIndex", "numvmail", "tdmins", "tdcalls", "temins", "tecalls", "tnmins", "tncalls", "timins", "ticalls", "numcs")

    // Crearting vector assembler
    val assembler = new VectorAssembler()
      .setInputCols(featureCols)
      .setOutputCol("features")

    // ML model to try out
    val dTree = new DecisionTreeClassifier().setLabelCol("label")
      .setFeaturesCol("features")

    /// Pipelines of Transformations
    val pipeline = new Pipeline().setStages(Array(ipindexer, labelindexer, assembler, dTree))

    // Hyperparam Tuning
    val paramGrid = new ParamGridBuilder()
      .addGrid(dTree.maxDepth, Array(2, 3, 4, 5, 6, 7))
      .build()

    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("label")
      .setRawPredictionCol("prediction")

    // Set up 3-fold cross validation
    val crossval = new CrossValidator()
      .setEstimator(pipeline)
      .setEvaluator(evaluator)
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(3)

    val cvModel = crossval.fit(ntrain)

    // Getting Best Model
    val bestModel = cvModel.bestModel
    println("The Best Model and Parameters:\n--------------------")
    println(bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel].stages(3))
    //bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel]
    //  .stages(3)
    //  .extractParamMap

    val treeModel = bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel].stages(3).asInstanceOf[DecisionTreeClassificationModel]
    println("Learned classification tree model:\n" + treeModel.toDebugString)

    val predictions = cvModel.transform(test)
    val accuracy = evaluator.evaluate(predictions)
    evaluator.explainParams()

    val predictionAndLabels = predictions.select("prediction", "label").rdd.map(x =>
      (x(0).asInstanceOf[Double], x(1).asInstanceOf[Double]))

    val metrics = new BinaryClassificationMetrics(predictionAndLabels)
    println("area under the precision-recall curve: " + metrics.areaUnderPR)
    println("area under the receiver operating characteristic (ROC) curve : " + metrics.areaUnderROC)

    println(metrics.fMeasureByThreshold())

    val result = predictions.select("label", "prediction", "probability")
    result.show
    
    val lp = predictions.select("label", "prediction")
    val counttotal = predictions.count()
    val correct = lp.filter($"label" === $"prediction").count()
    val wrong = lp.filter(not($"label" === $"prediction")).count()
    val ratioWrong = wrong.toDouble / counttotal.toDouble
    val ratioCorrect = correct.toDouble / counttotal.toDouble
    val truep = lp.filter($"prediction" === 0.0).filter($"label" === $"prediction").count() / counttotal.toDouble
    val truen = lp.filter($"prediction" === 1.0).filter($"label" === $"prediction").count() / counttotal.toDouble
    val falsep = lp.filter($"prediction" === 1.0).filter(not($"label" === $"prediction")).count() / counttotal.toDouble
    val falsen = lp.filter($"prediction" === 0.0).filter(not($"label" === $"prediction")).count() / counttotal.toDouble

    println("counttotal : " + counttotal)
    println("correct : " + correct)
    println("wrong: " + wrong)
    println("ratio wrong: " + ratioWrong)
    println("ratio correct: " + ratioCorrect)
    println("ratio true positive : " + truep)
    println("ratio false positive : " + falsep)
    println("ratio true negative : " + truen)
    println("ratio false negative : " + falsen)
    
  }

}