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

>(4) Implement a `drop` function

```scala
def drop[A](list : List[A], n : Int) : List[A] = {
  if(n <= 0) list
  else list match{
    case Nil => list
    case Cons(_, t) => drop(t, n-1)
  }
}
```

> (5) Implement a `dropWhile` function

```scala
def dropWhile[A](list : List[A], f: A => Boolean) : List[A] = list match {
  case Nil => list
  case Cons(h, t) => if( f(h) ) dropWhile(t, f) else list
}
```

> (6) Implement a function `init` that returns all elements of a list but the last one.

```scala
def init[A](list : List[A]) : List[A] = list match {
  case Nil => Nil
  case Cons(h, Nil) => Nil
  case Cons(h, t) => Cons(h, init(t))
}
```

> (9) Compute the length of a list using `foldRight`.

```scala
def foldRight[A,B](list : List[A], z : B)(f : (A, B) => B) : B = list match {
  case Nil => z
  case Cons(h, t) => f(h, foldRight(t,z)(f))
}

def length[A](list : List[A]) = foldRight(list, 0)((_ , acc) => acc + 1)
```

> (10) Write a tail-recursive function  `foldLeft`.

```scala
@annotation.tailrec
def foldLeft[A, B](list : List[A], z : B)(f : (B, A) => B) : B = list match{
  case Nil => z
  case Cons(h, t) => foldLeft(t, f(z, h))(f)
}
```

> (11) Write `sum`, `product` and a function to compute the length of a list using `foldLeft`

```scala
def sum(list : List[Int]) = foldLeft(list, 0)(_+_)
def product(list : List[Double]) = foldLeft(list, 1)(_*_)
def length[A](list : List[A]) = foldLeft(list, 0)((_, acc) => acc + 1)
```

> (12) Write a `reverse` function.

```scala
def reverse[A](list : List[A]) = foldLeft(list, List[])((h, acc) => Cons(acc, h)) 
```
> (13) Write `foldLeft` in terms of `foldRight`

```scala
// Maybe later.
```

> (14) Write `append` in terms of a fold

```scala
def append[A](l: List[A], r : List[A]) = foldRight(l, r)(Cons(_,_)) 
```

