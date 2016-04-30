## Chapter 4
### Keypoints:
  * A Map can be defined via new or by using the apply function with arguments (a set of tuples)
  * Tuples can be declared as `(x,y)` or `x -> y`.
  * Use `getOrElse(key, def)` to get a default value (`def`) when a key is not available
  * Java Map implementations can be used by taking advantage of the `scala.collection.JavaConversions` package.

### Exercises

> (1) Set up a map of prices for a number of gizmos that you covet. Then produce a second map with the same keys and the prices at a 10 percent discount

```scala
val prices = Map("Mac Pro" -> 2000, "James Bond suit" -> 5600, "Laser shooting shark" -> 5900)
for( (k , v) <- prices ) yield ( k, v * 0.9 )
```

> (2) Write a program that reads words from a file. Use a mutable map to count how often each word appears. To read the words, simply use a `java.util.Scanner`

```scala
val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
val count = scala.collection.mutable.HashMap[String,Int]
while(in.hasNext){
  val word = in.next
  count(word) = count.getOrElse(word, 0) + 1
}
count
```
> (3) Repeat the preceding exercise with an immutable map.

```scala
val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
var count = Map[String,Int]()
while(in.hasNext){
  val word = in.next
  count = count + (word -> (count.getOrElse(word, 0) + 1))
}
count
```

> (4) Repeat the preceding exercise with a sorted map, so that the words are printed in sorted order.

```scala
val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
var count = scala.collection.immutable.SortedMap[String,Int]()
while(in.hasNext){
  val word = in.next
  count = count + (word -> (count.getOrElse(word, 0) + 1))
}
count
```

> (5) Repeat the preceding exercise with a java.util.TreeMap that you adapt to the Scala API.

```scala
import scala.collection.JavaConversions.mapAsScalaMap
val in = new java.util.Scanner(new java.io.File("lipsum.txt"))
val count : scala.collection.mutable.Map[String,Int] = new java.util.TreeMap[String, Int]
while(in.hasNext){
  val word = in.next
  count(word) = count.getOrElse(word, 0) + 1
}
count
```

> (7) Print a table of all Java properties...

```scala
import scala.collection.JavaConversions.propertiesAsScalaMap
val props : scala.collection.Map[String,String] = System.getProperties
val pad = props.keys.map(_.length).max + 5
for( (k, v) <- props )
  printf("%-" + pad + "s| %s\n", k, v)
```

> (8) Write a function minmax(values: Array[Int]) that returns a pair containing the smallest and largest values in the array.

```scala
def minmax(a : Array[Int]) = (a.min, a.max)
```

> (9) Write a function `lteqgt(values: Array[Int], v: Int)` that returns a triple containing the counts of values less than `v`, equal to `v`, and greater than `v`.

```scala
def lteqgt(a: Array[Int], x : Int) = (a.count( _ < x ), a.count( _ == x ), a.count( _ > x ))
```
