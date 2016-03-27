## Chapter 11: Operators

### Keypoints

* Use the `unary_`*operator* syntax to support unary operators. Only `+ - ! ~` are allowed as *prefix* operators.
* The `apply` and `unapply` methods are called when using an object as a function. The called method depends on whether the call is on the left or right side of an assignment.
* `unapplySeq` can be used to extract an arbitrary sequence of values. It returns an `Option[Seq[A]]`. See ABN for this chapter.

```scala
import scala.math.abs

object Fraction{
	def apply(num : Int, denom : Int) = new Fraction(num, denom)
}

class Fraction(n : Int, d : Int){
	
	private val num : Int = if(d == 0) 1 else n / gcd(n, d)
	private val denom : Int = if(d == 0) 0 else d / gcd(n, d)
		
	def +(that : Fraction) = Fraction(num * that.denom + that.num * denom, denom * that.denom)
	def *(that : Fraction) = Fraction(num * that.num, denom * that.denom)
	def /(that : Fraction) = Fraction(num * that.denom, that.num * denom)
	def -(that : Fraction) = Fraction(num * that.denom - that.num * denom, denom * that.denom)
	
	override def toString = {
		if(denom == 0)
			"oo"
		else if(num == 0)
			"0"
		else
			"%s%d / %d".format(sign, abs(num), abs(denom))
	}

	def gcd(a : Int, b : Int) : Int = if (b == 0) a else gcd(b, a % b)
	def sign(x : Int) = if(x < 0) -1 else 1
	def sign : String = if(sign(num) != sign(denom)) "-" else ""
}
```

```scala
class BitSequence(private var mask : Long = 0L){
	def apply(x : Int) = mask & 1 << x
	
	def update(p : Int, x : Int) {
		mask = if(x == 0) mask & ~apply(p) else mask | 1 << p
		mask
	}

	override def toString = mask.toString
}
```

### A brief note on `unapply` and `unapplySeq`

An extractor is an object with an `unapply` method. It can be seen as an opposite of the `apply` method, it takes an object and extracts values from it. `unapplySeq` allows to extract an arbitrary sequence of values from an object. It is used with pattern matching, so keep tuning in for more.

```scala
class Person(val name : String)

object Person{
	def apply(name : String) = new Person(name) 

	def unapply(input : Person) = {
		if( input.name.trim.isEmpty || input.name.trim.indexOf(" ") == -1)
			None
		else{
			val tokens = input.name.split("\\s+" , 2)
			Some((tokens(0), tokens(1)))
		}
	}
}

val s = Person("lisbeth salander")
val Person(fname, lname) = s
```





















