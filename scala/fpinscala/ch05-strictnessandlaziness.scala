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
    case Cons(h, t) if n >= 1 => t().drop(n - 1)
    case _ => this
  }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

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
  def takeWhileUsingFoldRight(p : A => Boolean) : Stream[A] =
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

  def find(p: A => Boolean) : Option[A] =
    filter(p).headOption

  //5.13
  def mapUsingUnfold[B](f : A => B) : Stream[B] =
    unfold(this){
      case Cons(h, t) => Some((f(h()), t()))
      case _ => None
    }

  def takeUsingUnfold(n : Int) : Stream[A] =
    unfold((this, n)){
      case (Cons(h, t), 1) => Some((h(),(empty, 0))) // Avoid evaluating the stream if it's unnecessary
      case (Cons(h, t), n) if n > 1 => Some((h(),(t(), n - 1)))
      case _ => None
    }

  def takeWhileUsingUnfold(p : A => Boolean) : Stream[A] =
    unfold(this){
      case Cons(h, t) if p(h()) => Some((h(), t()))
      case _ => None
    }

  def zipWith[B,C](s2: Stream[B])(f: (A,B) => C): Stream[C] =
    unfold((this, s2)) {
      case (Cons(h1,t1), Cons(h2,t2)) => Some((f(h1(), h2()), (t1(), t2())))
      case _ => None
    }

  def zip[B](s2: Stream[B]): Stream[(A,B)] =
    zipWith(s2)((_,_))

  def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])] =
    zipWithAll(s2)(_ -> _)

  def zipWithAll[B, C](s2: Stream[B])(f : (Option[A], Option[B]) => C ): Stream[C] =
  unfold((this, s2)){
    case (Empty, Empty) => None
    case (Cons(h,t), Empty) => Some(f(Some(h()), Option.empty[B]) -> (t(), empty[B]))
    case (Empty, Cons(h,t)) => Some(f(Option.empty[A], Some(h())) -> (empty[A], t()))
    case (Cons(h1, t1), Cons(h2, t2)) => Some(f(Some(h1()), Some(h2())) -> (t1(), t2()))
  }

  //5.14
  def startsWith[A](s : Stream[A]) : Boolean =
    zipAll(s) takeWhile(!_._2.isEmpty) forAll { case (h1, h2) => h1 == h2}

  def tails : Stream[Stream[A]] =
    unfold(this){
      case Empty => None
      case s => Some(s, s drop 1)
    }.append(empty)

  //I'd say this function should be called hasSubstring.
  def hasSubsequence[A](s : Stream[A]) : Boolean =
    tails exists (_ startsWith s )

  //5.15
  def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] =
    foldRight((z, Stream(z)))((a, p0) => {
      lazy val p1 = p0
      val b2 = f(a, p1._1)
      (b2, cons(b2, p1._2))
    })._2

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

  //5.8
  def constantRecursive[A](a : A) : Stream[A] = cons(a, constantRecursive(a))

  def constantLazy[A](a : A) : Stream[A] = {
    lazy val tail : Stream[A] = Cons(() => a, () => tail)
    tail
  }

  //5.9
  def from(n : Int) : Stream[Int] =
    cons(n, from(n + 1))

  //5.10
  val fibs : Stream[Int] = {
    def go(a : Int, b : Int) : Stream[Int] = cons(a, go(b, a + b))
    go(0, 1)
  }

  //5.11
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match{
    case None => empty
    case Some((a,s)) => cons(a, unfold(s)(f))
  }

  //5.12
  val fibs2 : Stream[Int] =
    unfold((0,1)){ case (a,b) => Some((a, (b, a+b))) }

  def from2(n : Int) : Stream[Int] =
    unfold(n)(n => Some(n, n + 1))

  def constant2(n: Int) : Stream[Int] =
    unfold(n)(_ => Some((n, n)))

  def ones2 : Stream[Int] =
    unfold(1)(_ => Some((1,1)))
}

