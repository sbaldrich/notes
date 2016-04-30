## Chapter 3: Functional Data Structures

### Exercises

>(0) Let's set up a List

```scala
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head : A, tail: List[A]) extends List[A]

object List{
  def sum(ints : List[Int]) : Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds : List[Double]) : Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*) : List[A] = if(as.isEmpty) Nil else Cons(as.head, apply(as.tail: _*))
}
```

>(1) What will the result of the following match be?

```scala
val x = List(1,2,3,4,5) match {
  case Cons(x, Cons(2, Cons(4, _))) => x
  case Nil => 42
  case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y // => this one: 3
  case Cons(h, t) => h + sum(t)
  case _ => 101
}
```

> (2) Implement `tail` for removing the first element in a list in O(1)

```scala
def tail[A](list : List[A]) : List[A] = list match{
  case Nil => Nil
  case Cons(_, t) => t
}
```

>(3) Implement a `setHead` function

```scala
def setHead[A](list : List[A], head : A) : List[A] = list match {
  case Nil => Cons(head, Nil)
  case Cons(_, t) => Cons(head, t)
}
```
