package io.github.ashayking.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object RetailExample {

  def main(args: Array[String]): Unit = {
    val fileName = "/home/ashay/DBDOCKER/data/retail_db/order_items"

    // Creating spark context
    val sparkConf = new SparkConf()
    val sc = new SparkContext("local[2]", "RDDCollections", sparkConf)

    val orderItems = sc.textFile(fileName)

    // ACTION1
    println(orderItems.count())

    // ACTION2
    println(orderItems.first())

    // ACTION3
    val orderItemSubTotals = orderItems.map(rec => rec.split(",")(4).toFloat)

    // Lets do sum
    // way1
    println(orderItemSubTotals.sum())
    // way2
    val sum = orderItemSubTotals.reduce((tot, ele) => tot + ele)
    println(sum)
  }
}