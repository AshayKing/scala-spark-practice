package io.github.ashayking.spark.sql.ml.customerchurn

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset

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

    train.take(1)
    train.cache
    println(train.count)

    // Testing Dataset
    val test: Dataset[Account] = spark
      .read
      .option("inferSchema", "false")
      .schema(Models.schema)
      .csv(testFilePath).as[Account]
    
    test.take(2)
    test.cache
    println(test.count)

    // Printing Schema & Some data
    train.printSchema()
    train.show
  }

}