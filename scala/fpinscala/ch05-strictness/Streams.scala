import Stream._

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

trait Stream[+A]{
  //5.1
  def toList : List[A] = this match{
    case Cons(h, t) => h() :: t().toList
    case _ => List()
  }

  //5.2
  def take(n : Int) : Stream[A] = this match{
    case Cons(h , t) if n > 1  =>  cons(h(), t().take(n - 1))
    case Cons(h , _) if n == 1 =>  cons(h(), empty)
    case _ => empty
  }

  def drop(n : Int) : Stream[A] = this match{
    case Cons(h, t) if n > 1 => t().drop(n - 1)
    case _ => this
  }

  //5.3
  def takeWhile(p : A => Boolean) : Stream[A] = this match{
    case Cons(h, t) if(p(h())) => cons(h(), t().takeWhile(p))
    case _ => empty
  }

  def foldRight[B](z : => B)(f : (A, => B) => B) : B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }
  //5.4
  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((x, y) => p(x) && y)

  //5.5
  def takeWhile2(p : A => Boolean) : Stream[A] =
    foldRight(empty[A])((x, y) => if(p(x)) cons(x, y) else empty)

  //5.6
  def headOption : Option[A] =
    foldRight(None : Option[A])((h, _) => Some(h))

  //5.7
  def map[B](f : A => B) : Stream[B] =
    foldRight(empty[B])((h, t) => cons(f(h), t))

  def filter(f : A => Boolean) : Stream[A] =
    foldRight(empty[A])((h, t) => if(f(h)) cons(h, t) else t)

  def append[B >:A](s : => Stream[B]) : Stream[B] =
    foldRight(s)((h,t) => cons(h,t))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])((h,t) => f(h) append t)
}


object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))
}
