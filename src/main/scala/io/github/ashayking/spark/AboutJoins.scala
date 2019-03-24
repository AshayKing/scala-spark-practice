package io.github.ashayking.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object AboutJoins {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    val sc = new SparkContext("local[2]", "RDDCollections", sparkConf)

    val ordersData = sc.textFile("/home/ashay/DBDOCKER/data/retail_db/orders")
    val orderItemsData = sc.textFile("/home/ashay/DBDOCKER/data/retail_db/order_items")

    // Must convert to (K,V)
    val orders = ordersData.map(rec => (rec.split(",")(0).toInt, rec.split(",")(1)))

    val orderItems = orderItemsData.map(rec => (rec.split(",")(1).toInt, rec.split(",")(4).toFloat))

    // Inner Join
    val ordersJoin = orders.join(orderItems)
    println("Inner Join size : " + ordersJoin.count())

    ordersJoin
      .take(10)
      .map(rec => (rec._1, rec._2._1 + ":" + rec._2._2))
      .foreach(println)

    // Left Outer Join
    val ordersLeftOuterJoin = orders.leftOuterJoin(orderItems)
    println("Left Outer Join size : " + ordersLeftOuterJoin.count())

    // Right Outer
    // Cartesian
    // Full Outer

    // Find Distinct Items soled in 2013-12
    val order201312 = ordersData
      .filter(order => order.split(",")(1).contains("2013-12"))
      .map(rec => (rec.split(",")(0).toInt, rec.split(",")(1)))

    val order201401 = ordersData
      .filter(order => order.split(",")(1).contains("2014-01"))
      .map(rec => (rec.split(",")(0).toInt, rec.split(",")(1)))

    val orderItemWithProdIds = orderItemsData.map(rec => (rec.split(",")(1).toInt, rec.split(",")(2).toInt))

    val product201312 = order201312
      .join(orderItemWithProdIds)
      .map(order => order._2._2)

    val product201401 = order201401
      .join(orderItemWithProdIds)
      .map(order => order._2._2)

    val distinctProdInBothMonthsCount = product201312.union(product201401).distinct().count()
    println(distinctProdInBothMonthsCount)

    val distinctCommonProdInBothMonthsCount = product201312.intersection(product201401).count()
    println(distinctCommonProdInBothMonthsCount)
    
    // Sorting
    val sortByCustId = ordersData
    .map(rec => (rec.split(",")(2).toInt, rec))
    .sortByKey()
    .take(20)
    .foreach(println)
    
    // grouping
    val groupByCustId = ordersData
    .map(rec => (rec.split(",")(2).toInt, rec))
    .groupByKey()
    .take(20)
    .foreach(println)
  }

}