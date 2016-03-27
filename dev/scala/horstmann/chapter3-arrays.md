## Chapter 3
### Keypoints:
  * `if` has a type. If a branch returns `Nothing` the type of the statement is the one of the other branch.
  * Exceptions are of type `Nothing`.
  * There are no checked exceptions in Scala.
  * Use `yield` to generate an array from a `for` statement.


> (1) Write a code snippet that sets a to an array of n random integers between 0 (inclusive) and n (exclusive)

```scala
def randArray(n : Int) = (for(_ <- 0 until n ) yield scala.util.Random.nextInt(n)).toArray
```

> (2) Write a loop that swaps adjacent elements of an array of integers. For example, `Array(1, 2, 3, 4, 5)` becomes `Array(2, 1, 4, 3, 5)`.

```scala
//I laugh at your loop
def swapAdj(array : Array[Int]) = array.grouped(2).flatMap(_.reverse).toArray
```

> (4) Given an array of integers, produce a new array that contains all positive values of the original array, in their original order, followed by all values that are zero or negative, in their original order.

```scala
def posThenNeg(array : Array[Int]) = array.filter( _ >= 0 ) ++ array.filter( _ < 0 )
```

> (9) Make a collection of all time zones returned by `java.util.TimeZone.getAvailableIDs` that are in America. Strip off the "America/" prefix and sort the result.

```scala
//Can also use drop instead of substring
java.util.TimeZone.getAvailableIDs.filter(_.startsWith("America/")).map(_.substring("America/".length)).sorted
```
