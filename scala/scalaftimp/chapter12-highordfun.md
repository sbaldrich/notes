## Chapter 12: Higher order functions

### Keypoints

* Currying does not refer to delicious indian food :(
* Use *call by value* to defer the evaluation. See ABN.

### Exercises

> (1) Write a function `values(fun: (Int) => Int, low: Int, high: Int)` that yields a collection of function inputs and outputs in a given range. For example, `values(x => x * x, -5, 5)` should produce a collection of pairs `(-5, 25), (-4, 16), (-3, 9), . . ., (5, 25)`.

```scala
def values(fun : Int => Int, low: Int, high : Int) = for( x <- low to high ) yield (x, fun(x))
```
> (2) How do you get the largest element of an array with `reduceLeft`?

```scala
val seq = Seq.fill(10)(scala.util.Random.nextInt(20))
val largest = seq.reduceLeft((x,y) => if(x > y) x else y)
```
> (3) Implement the factorial function using `to` and `reduceLeft`, without a loop or recursion.

```scala
def factorial(n : Int) = {
	if(n > 0) (1 to n).reduceLeft(_ * _)
	else -1 * (1 to scala.math.abs(n)).reduceLeft(_ * _)
}
```
> (4) The previous implementation needed a special case when `n < 1`. Show how you can avoid this with `foldLeft`. (Look at the Scaladoc for `foldLeft`. It’s like `reduceLeft`, except that the first value in the chain of combined values is supplied in the call.)

```scala
import scala.math.abs
def factorial(n : Int) = (1 to abs(n)).foldLeft(n / abs(n))(_ * _)
```
> (5) Write a function `largest(fun: (Int) => Int, inputs: Seq[Int])` that yields the largest value of a function within a given sequence of inputs. For example, `largest(x => 10 * x - x * x, 1 to 10)` should return 25. Don’t use a loop or recursion.

```scala
def largest(fun : (Int) => Int, inputs: Seq[Int]) = inputs.map(fun(_)).max
```
> (6) Modify the previous function to return the input at which the output is largest. For example, `largestAt(fun: (Int) => Int, inputs: Seq[Int])` should return 5. Don’t use a loop or recursion.

```scala
def largestAt(fun : (Int) => Int, inputs: Seq[Int]) = inputs.reduce((x,y) => if(fun(x) > fun(y)) x else y)
```
> (7) It’s easy to get a sequence of pairs, for example
`val pairs = (1 to 10) zip (11 to 20)`
Now suppose you want to do something with such a sequence —say, add up the values. But you can’t do `pairs.map(_ + _)`. The function `_ + _` takes two `Int` parameters, not an `(Int, Int)` pair. Write a function `adjustToPair` that receives a function of type `(Int, Int) => Int` and returns the equivalent function that operates on a pair. For example, `adjustToPair(_ * _)((6, 7))` is 42.
Then use this function in conjunction with map to compute the sums of the elements in pairs.

```scala
def adjustToPair(fun : (Int, Int) => Int) = (tup : (Int, Int)) => fun(tup._1, tup._2)
val vals = List.fill(10)((Random.nextInt(10), Random.nextInt(10)))
vals.map(adjustToPair(_ + _))
```
> (8) In Section 12.8, “Currying,” on page 149, you saw the `corresponds` method used with two arrays of strings. Make a call to `corresponds` that checks whether the elements in an array of strings have the lengths given in an array of integers.

```scala
val str = Array("Hello", "Scala", "World")
val len = Array.fill(3)(5)
str.corresponds(len)((x,y) => (x.length == y))
```

### A Brief Note on Currying and Control Abstractions

The following is the definition of an `until` statement, it works as a `while` statement but the condition is inverted. Notice the use of currying to ensure a natural use as well as the call by name to avoid the condition to be evaluated prematurely and having to use a `() =>` syntax for the code block.

```scala
def until(condition : => Boolean )(block : => Unit){ //Call by value
	if( !condition ){
		block
		until(condition)(block)
	}
}

var x = 10
until (x == 0) {
	x -= 1
	println(x)
}
```
