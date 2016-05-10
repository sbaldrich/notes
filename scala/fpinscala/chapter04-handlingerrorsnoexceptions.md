## Chapter 4: Handling Errors Without Exceptions

### Exercises

>(1) Let's set up an `Option` trait and define some useful functions on it.

```scala
import scala.{Option => _, Either => _, Some => _}

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }

  def getOrElse[B >: A](default : => B) = this match{
    case None => default
    case Some(a) => a
  }

  def flatMap[B](f : A => Option[B]) : Option[B] = map(f) getOrElse None

  def orElse[B >: A](default : => Option[B]) : Option[B] = this map (Some(_)) getOrElse default

  def filter(f : A => Boolean) : Option[A] = flatMap(x => if(f(x)) Some(x) else None)
}

case object None extends Option[Nothing]
case class Some[+A](get: A) extends Option[A]
```

> (2) Implement the `variance` function in terms of `flatMap`.

```scala
def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
def variance(xs: Seq[Double]): Option[Double] = mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
```

> (3) Write a generic `map2` function that combines two `Option` using a binary function.

```scala
def map2[A,B,C](a: Option[A], b: Option[B])(f : (A,B) => C) : Option[C] =
    a flatMap (aa => b map (bb => f(aa, bb)))
```

> (4) Write a function `sequence` that takes a `List` of options and returns an `Option` containing a list of
all `Some` values. The return value must be `None` if the list contains at least one `None` value. This function shouldn't be
defined inside `List` because it need not know anything about Options. Also including it inside the trait makes no sense, so it
should be defined inside the `Option` companion object.

```scala
def sequence[A](a : List[Option[A]]) : Option[List[A]] = a match{
  case Nil => Some(Nil)
  case h :: t => h flatMap(hh => sequence(t) map (hh :: _))
}
```
