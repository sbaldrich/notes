## Chapter 14: Case classes and Pattern matching

### Keypoints:

* Make sure your case classes extend a `sealed` class to let the compiler check whether all alternatives were exhausted on a `match` statement.
* Matching nested classes is possible (and kind of cool), see ABN.
* In a `match` statement, use `@` to bind nested values in a pattern to a variable.

### Exercises

> (2) Using pattern matching, write a function `swap` that receives a pair of integers and returns the pair with the components swapped.

```scala
def swap( pair : (Int, Int)) = pair match {case (x,y) => (y,x)}
```
> (3) Using pattern matching, write a function `swap` that swaps the first two elements of an array provided its length is at least two.

```scala
def swap(input : Array[Int]) = input match {
  case Array(x, y, rest @ _*) => Array(y, x) ++ rest
  case _ => input
}
```

### A Brief Note on pattern matching

Your OO neurons are going to explode but look at how freaking cool this example is!

```scala
sealed abstract class Item
case class Article(description : String, price : Double) extends Item
case class Bundle(description : String, discount : Double, items : Item*) extends Item

val bundle = Bundle("The Christmas Special", 10.0, Article("Ugly Sweater", 20.0),
  Bundle("Ancheta", 0.0, Article("Aguardiente", 10.0), Article("Gross panettone", 5.0)))

bundle match{
  case Bundle(_, _, Article(descr, _), _*) => descr // The description of the first Article in a Bundle
}

def price(item : Item) : Double = item match {
  case Article( _, p ) => p
  case Bundle( _, disc, its @ _* ) => its.map( price _ ).sum - disc
}

price(bundle) // 25.0
```
