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
    // println(orderItems.count())

    // ACTION2
    // println(orderItems.first())

    // ACTION3
    // val orderItemSubTotals = orderItems.map(rec => rec.split(",")(4).toFloat)

    // Lets do sum
    // way1
    // println(orderItemSubTotals.sum())
    // way2
    // val sum = orderItemSubTotals.reduce((tot, ele) => tot + ele)
    // println(sum)

    val orderFileName = "/home/ashay/DBDOCKER/data/retail_db/orders"
    val orders = sc.textFile(orderFileName)

    println(orders.first())

    //fetching COMPLETED orders
    orders.filter(rec => rec.split(",")(3) == "COMPLETE")
      .map(rec => (rec.split(",")(1), 1))
      //.groupBy(rec => rec._1)
      .reduceByKey(_ + _)
      .take(5)
      .foreach(println)

    // Agg by Key

    orderItems
      .map(oItem => (oItem.split(",")(1).toInt, oItem.split(",")(4).toFloat))
      .take(10)
      .foreach(println)

    orderItems
      .map(oItem => (oItem.split(",")(1).toInt, oItem.split(",")(4).toFloat))
      // ele here will be value of key i.e. revenue for key:orderId
      .aggregateByKey((0.0, 0))(
        (total, ele) => (total._1 + ele, total._2 + 1), // will be performed on each partition
        (total, internal) => (total._1 + internal._1, total._2 + internal._2) // performed of intiator
      )
      .take(10)
      .foreach(println)

  }
}