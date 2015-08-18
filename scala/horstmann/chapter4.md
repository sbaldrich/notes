## Chapter 4
### Keypoints:
  * A Map can be defined via new or by using the apply function with arguments (a set of tuples)
  * Tuples can be declared as `(x,y)` or `x -> y`.
  * Use `getOrElse(key, def)` to get a default value (`def`) when a key is not available
  * Java Map implementations can be used by taking advantage of the `scala.collection.JavaConversions` package.

```scala
def ex1() = {
  val prices = Map("Mac Pro" -> 2000, "James Bond suit" -> 5600, "Laser shooting shark" -> 5900)
  for((k,v) <- prices) yield (k, v * 0.9)
}
```
```scala
def ex2() = {
  val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
  val count = scala.collection.mutable.HashMap[String,Int]
  while(in.hasNext){
    val word = in.next
    count(word) = count.getOrElse(word, 0) + 1
  }
  count
}
```
```scala
def ex3() = {
  val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
  var count = Map[String,Int]()
  while(in.hasNext){
    val word = in.next
    count = count + (word -> (count.getOrElse(word, 0) + 1))
  }
  count
}
```
```scala
def ex4() = {
  val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
  var count = scala.collection.immutable.SortedMap[String,Int]()
  while(in.hasNext){
    val word = in.next
    count = count + (word -> (count.getOrElse(word, 0) + 1))
  }
  count
}
```
```scala
def ex5() = {
  import scala.collection.JavaConversions.mapAsScalaMap
  val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
  val count : scala.collection.mutable.Map[String,Int] = new java.util.TreeMap[String, Int]
  while(in.hasNext){
    val word = in.next
    count(word) = count.getOrElse(word, 0) + 1
  }
  count
}
```
```scala
def ex6(){
  import scala.collection.JavaConversions.propertiesAsScalaMap
  val props : scala.collection.Map[String,String] = System.getProperties
  val pad = props.keys.map(_.length).max + 5
  for((k,v) <- props)
    printf("%-" + pad +"s| %s\n", k, v)
}
```
```scala
//ex 8
def minmax(a : Array[Int]) = (a.min, a.max)
```
```scala
//ex 9
def lteqgt(a: Array[Int], x : Int) = (a.count( _ < x ), a.count( _ == x ), a.count( _ > x ))
```
