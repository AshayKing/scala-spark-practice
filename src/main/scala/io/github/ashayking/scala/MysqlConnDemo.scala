package io.github.ashayking.scala

import java.sql.DriverManager

object MysqlConnDemo {

  def main(args: Array[String]): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val host = "localhost"
    val port = "3306"
    val db = "geeky_mode"

    val url = s"jdbc:mysql://$host:$port/$db"
    val username = "coreplatform"
    val password = "CorePlatform@123"

    Class.forName(driver)
    val connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()

    val resultSet = statement.executeQuery("SELECT id, name FROM employee")
    while (resultSet.next()) {
      val emp = Employee(resultSet.getInt("id"), resultSet.getString("name"))
      println(emp)
    }

    connection.close()
  }
}