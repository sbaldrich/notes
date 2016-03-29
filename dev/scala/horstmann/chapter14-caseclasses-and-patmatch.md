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

> (4) Add a case class Multiple that is a subclass of the Item class. For example, `Multiple(10, Article("Blackwell Toaster", 29.95))` describes ten toasters. Of course, you should be able to handle any items, such as bundles or multiples, in the second argument. Extend the price function to handle this new case.

```scala
sealed abstract class Item
case class Article(description : String, price : Double) extends Item
case class Bundle(description : String, discount : Double, items : Item*) extends Item
case class Multiple(ammount : Int, items : Item*) extends Item

val bundle = Bundle("The Christmas Special", 10.0, Article("Ugly Sweater", 20.0),
  Bundle("Ancheta", 0.0, Article("Aguardiente", 10.0), Article("Gross panettone", 5.0)))

bundle match{
  case Bundle(_, _, Article(descr, _), _*) => descr // The description of the first Article in a Bundle
}

def price(item : Item) : Double = item match {
  case Article( _, p ) => p
  case Bundle( _, disc, its @ _* ) => its.map(price _).sum - disc
  case Multiple(amount, item) => amount * price(item)
}

price(Multiple(10, bundle))
```

> (5) A better way of modeling such trees is with case classes, write a function to calculate the sum of the leaves of a binary tree (the classes are given).

```scala
sealed abstract class BinaryTree
case class Leaf(value : Int) extends BinaryTree
case class Node(left : BinaryTree, right : BinaryTree) extends BinaryTree

def leafsum(tree : BinaryTree) : Int = tree match{
  case Leaf(value) => value
  case Node(left, right) => leafsum(left) + leafsum(right)
}

val tree = Node(Node(Leaf(1), Leaf(2)), Node(Node(Leaf(3), Leaf(5)), Leaf(4)))

leafsum(tree)

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
