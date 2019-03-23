package io.github.ashayking.scala

object DataTypes {
  def main(args: Array[String]): Unit = {
    // Declaring immutable variable
    val a = 100
    // a=100 error

    // mutable variable
    var b = 10
    b = 20

    // explicitly giving data type
    val str: String = "Ashay"

    /**
     * Data type supported in Scala
     * Boolean
     * Char
     * String
     * Byte
     * Short
     * Long
     * Int
     * Float
     * Double
     *
     * Unit : like void in java
     */

    val bool: Boolean = true
    println(bool)

    val char: Char = 'A'
    println(char)

    val intData: Int = 100
    println(intData)

    // conversion
    println(intData.floatValue())

    // In scala there is no concept of primitive
    // All things defined here are in context of object
    // scala compiles to jvm bytecode
    // it can assign a primitive while performing optimization in compilation

    // All is method
    println(a + 10)
    println(a.+(10))

    // No += support

    // How to print
    println("This is variable " + a) // leagacy : String concatenation
    println(s"This is variable $a") // preferred : String interpolation

    // expression
    val aEx = {
      val temp1 = (math.random * 100).toInt
      val temp2 = (math.random * 100).toInt
      temp2 - temp1
    }
    println(aEx)
  }
}