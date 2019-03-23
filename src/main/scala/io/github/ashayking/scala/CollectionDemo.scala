package io.github.ashayking.scala

object CollectionDemo {

  def main(args: Array[String]): Unit = {
    // Traversable is top-most Train for Collections
    val t = Traversable(1, 2, 3)
    t.foreach(print)

    println()

    t foreach print

    // Collection Trait Hierarchy : Traversable -> Iterable -> [Seq, Set, Map]

    // List
    val list = List(1, 2, 3, 4, 5, 6, 7)

    println()
    val filtered = list filter (i => i % 2 == 0)
    filtered foreach print

    println()
    list.filter(i => i % 2 == 0).foreach(print)

    println()
    val append200tolist = 200 +: list
    append200tolist.foreach(print)

    println()
    val bigList = (0 to 1000).toList
    val sumData = bigList
      .filter(i => i % 2 == 0)
      .map(i => i * i)
      .reduce((total, ele) => total + ele)
    println(sumData)

    // Set
    val set = Set(1, 2, 3, 5, 2, 1, 4, 2)
    println(set)

    var mutableSet = scala.collection.mutable.Set(1, 3, 4)
    mutableSet.add(44)
    println(mutableSet)

    val s1 = Set(1, 9, 3, 5)
    val s2 = Set(1, 6, 8)
    println(s1 | s2)
    println(s1 & s2)
    println(s1 ++ s2)
    println(s1 ++ List(2,3,4,5))
    println(s1.toList.sorted)
    
    // Map
    val myMap = Map("k1"->"v1","k2"->"v2")
    println(myMap("k1"))
    println(myMap.getOrElse("k6", "default"))
    
    // Tuple
    val tup = ("ASHAY",100,"IND")
    val (name,id,cntry) = tup
    println(tup._1)
    println(name)
  }

}