## Chapter 4: Handling Errors Without Exceptions

### Exercises

>(1) Let's set up an `Option` trait and define some usefult functions on it.

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

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]
```

> (2) Implement the `variance` function in terms of `flatMap`.

```scala
def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
def variance(xs: Seq[Double]): Option[Double] = mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
```
