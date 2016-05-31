trait RNG{
  def nextInt : (Int, RNG)
}

object RNG{

  case class Simple(seed: Long) extends RNG {

    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = Simple(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }
    //6.1
    def nonNegativeInt(rng : RNG) : (Int, RNG) = {
      val (i, r) = rng.nextInt
      (if (i < 0) -(i + 1) else i, r)
    }
    //6.2
    def double(rng : RNG) : (Double, RNG) = {
      val (i, r) = nonNegativeInt(rng)
      (i / Int.MaxValue.toDouble + 1, r)
    }

    //6.3
    def intDouble(rng : RNG) : ((Int, Double), RNG) ={
      val (i, r) = nextInt
      val (d, r2) = double(r)
      ((i,d), r2)
    }

    def doubleInt(rng : RNG) : ((Double, Int), RNG) = {
      val ((i,d), r) = intDouble(rng)
      ((d, i), r)
    }

    def double3(rng : RNG) : ((Double, Double, Double), RNG) = {
      val (d1, r) = double(rng)
      val (d2, r1) = double(r)
      val (d3, r2) = double(r1)
      ((d1,d2,d3), r2)
    }
    //6.4
    def ints(count : Int)(rng : RNG) : (List[Int], RNG) =
      if(count == 0)
        (List(), rng)
      else{
        val(i, r) = nextInt
        val(xs, r1) = ints(count - 1)(r)
        ((i :: xs), r1)
      }

    type Rand[+A] = RNG => (A, RNG)

    val int : RNG => (Int, RNG) = _.nextInt

    def map[A,B](s : Rand[A])(f : A => B) : Rand[B] =
      rng => {
        val (a, rng2) = s(rng)
        (f(a),rng2)
      }

    def nonNegativeEven : Rand[Int] =
      map(nonNegativeInt)(i => i - i % 2)

  }
}
