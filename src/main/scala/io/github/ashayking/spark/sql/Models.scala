package io.github.ashayking.spark.sql

case class Order(
  order_id:          Int,
  order_date:        String,
  order_customer_id: Int,
  order_status:      String)

case class OrderItem(
  order_item_id:         Int,
  order_item_order_id:   Int,
  order_item_product_id: Int,
  order_item_quantity:   Int,
  order_item_subtotal:   Float,
  order_item_price:      Float)