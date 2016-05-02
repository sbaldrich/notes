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

> (15) Write a function that concatenates a list of lists into a single list in O(sum(len(list_i))).

```scala
def concat[A](l: List[List[A]]): List[A] = foldRight(l, Nil:List[A])(append)
```

> (16) Write a pure function that transforms a List of integers by adding 1 to each one.

```scala
def add(l : List[Int]) : List[Int] = foldRight(l, Nil:List[Int])((h,t) => Cons(h + 1, t))
```

> (17) Write a function that converts every element in a `List[Double]` to `String`.

```scala
def toString(l : List[Double]) : List[String] = foldRight(l, Nil:List[String])((h,t) => Cons(h.toString(), t))
```

> (18) Write a `map` function.

```scala
def map[A,B](l : List[A])(f : A => B) : List[B] = foldRight(l, Nil:List[B])((h,t) => Cons(f(h),t)) // Can you say stack overflow?
```

> (19) Write a `filter` function and use it to remove all odd elements from an integer list.

```scala
def filter[A](as : List[A])(f : A => Boolean) = foldRight(as, Nil:List[A])((h, t) => if(f(h)) Cons(h,t) else t)
filter(List(1,2,3,4,5))(_ % 2 == 0)
```

> (20) Write a `flatMap` function.

```scala
def flatMap[A,B](l : List[A])(f : A => List[B]) : List[B] = concat(map(l)(f))
```

> (21) Use `flatMap` to implement `filter`.

```scala
def filter2[A](l : List[A])(f : A => Boolean) : List[A] = flatMap(l)(h => if(f(h)) List(h) else Nil)
```

> (22) Write a function that takes two lists and creates a new list by adding corresponding elements

```scala
def zipSum(l : List[Int], r : List[Int]) : List[Int] = (l,r) match{
  case(Nil, _) => r
  case(_, Nil) => l
  case(Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, zipSum(t1, t2))
}
```

> (23) Generalize the previous function.

```scala

def zipWith[A, B, C](l : List[A], r : List[B])(f : (A, B) => C) : List[C] = (l,r) match{
  case(Nil, _) => Nil
  case(_, Nil) => Nil
  case(Cons(h1, t1), Cons(h2, t2)) => Cons(f(h1, h2), zipWith(t1, t2)(f))
}
```

> (24) Implement a `hasSubsequence` function.

```scala
@annotation.tailrec
def hasSubsequence[A](p : List[A], t : List[A]) : Boolean = (p, t) match {
  case(Nil, _) => true
  case(h, Nil) => false
  case(Cons(ph,pt), Cons(th, tt)) => if(ph == th) hasSubsequence(pt, tt) else hasSubsequence(Cons(ph, pt), tt)
}
```
