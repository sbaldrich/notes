## Chapter 12: Higher order functions

### Keypoints

* Currying does not refer to delicious indian food :(
* Use *call by value* to defer the evaluation. See ABN.

### Exercises
```scala
//1
def values(fun : Int => Int, low: Int, high : Int) = for( x <- low to high ) yield (x, fun(x))
```
```scala
//2
val seq = Seq.fill(10)(scala.util.Random.nextInt(20))
val largest = seq.reduceLeft((x,y) => if(x > y) x else y)
```
```scala
//3
def factorial(n : Int) = {
	if(n > 0)
		(1 to n).reduceLeft(_ * _)
	else -1 * (1 to scala.math.abs(n)).reduceLeft(_ * _)
}
```
```scala
//4
import scala.math.abs
def factorial(n : Int) = (1 to abs(n)).foldLeft(n / abs(n))(_ * _)
```
```scala
//6
def largest(fun : (Int) => Int, inputs: Seq[Int]) = inputs.reduceLeft((x,y) => if(fun(x) > fun(y)) x else y)
```
```scala
//7
def adjustToPair(fun : (Int, Int) => Int) = (tup : (Int, Int)) => fun(tup._1, tup._2)
val vals = List.fill(10)((Random.nextInt(10), Random.nextInt(10)))
vals.map(adjustToPair(_ + _))
```
```scala
//8
val str = Array("Hello", "Scala", "World")
val len = Array.fill(3)(5)
str.corresponds(len)((x,y) => (x.length == y))


```
### A Brief Note on Currying and Control Abstractions

*I'm still wrapping my head around the concept so I'll just leave this placeholder here and slowly leave the building*
```scala
def until(condition : => Boolean )(block : => Unit){ //Call by value
	if(!condition){
		block
		until(condition)(block)
	}
}

var x = 10
until (x == 0){
	x -= 1
	println(x)
}
```