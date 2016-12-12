## Chapter 13: Collections

### Keypoints:

* Remember that any operator finishing in `:` is right associative and must be a method of the right operand.
* Create `List`s with the `::` and `:::` operators and `Stream`s using the `#::` operator. Read ABN for this chapter.

### Exercises

> (1) Write a function that, given a string, produces a map of the indexes of all characters. For example, `indexes("Mississippi")` should return a map associating 'M' with the set {0}, 'i' with the set {1, 4, 7, 10}, and so on. Use a mutable map of characters to mutable sets. How can you ensure that the set is sorted?

```scala
import scala.collection.mutable.{LinkedHashMap => Map, LinkedHashSet => Set}

def indexes(s : String) = {
  (Map[Char, Set[Int]]() /: s.indices) {
    (m, i) => m(s(i)) = m.getOrElse(s(i), Set()) + i; m
  }
}

indexes("Mississipi")
```
> (3) Write a function that removes all zeroes from a linked list of integers.

```scala
def rmz(list : LinkedList[Int]) = list.filterNot( _ == 0)
```
> (4) Write a function that receives a collection of strings and a map from strings to integers. Return a collection of integers that are values of the map corresponding to one of the strings in the collection. For example, given `Array("Tom", "Fred", "Harry")` and `Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)`, return `Array(3, 5)`. Hint: Use `flatMap` to combine the Option values returned by `get`.

```scala
def f(s : Seq[String], m : Map[String, Int]) = s.flatMap(m.get(_))
```
> (5) Implement a function that works just like mkString, using reduceLeft.

```scala
def mkstr(seq : Seq[Any], sep : String = " ") = seq.reduceLeft((l,r) => l + sep + r )
```
> (8) Write a function that turns an array of `Double` values into a two-dimensional array. Pass the number of columns as a parameter. For example, with `Array(1, 2, 3, 4, 5, 6)` and three columns, return `Array(Array(1, 2, 3), Array(4, 5, 6))`. Use the `grouped` method.

```scala
def toMatrix(seq : Array[Double], n: Int) = {
  var matrix = new ArrayBuffer[Array[Double]]
  seq.grouped(n).foreach(x => matrix :+= x.toArray)
  matrix.toArray
}
```

### A Brief Note on Streams

The following snippet speaks for itself.

```scala
def numsFrom( n : BigInt ) : Stream[BigInt] = n #:: numsFrom(n + 1)
```
