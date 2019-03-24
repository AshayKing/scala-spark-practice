package io.github.ashayking.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.io.Source

object RDDCollections {
  
  def main(args: Array[String]): Unit = {
    
    // Creating spark context
    val sparkConf = new SparkConf()
    val sc = new SparkContext("local[2]","RDDCollections",sparkConf)

    val collection = (0 to 100)
    
    // Collection to RDD
    val rdd = sc.parallelize(collection)
    println(rdd.count())
    
    // RDD to Collection
    val rc = rdd.collect()
    println(rc.length)
    
    // Lets read from file
    val cards = Source.fromFile("src/main/resources/cards/deckofcards.txt")
    
    val cardList = cards.getLines.toList
    
    val rddOfCards = sc.parallelize(cardList)
    
    println(rddOfCards.count())
    
    rddOfCards.take(10).foreach(println)
    
  }
  
}