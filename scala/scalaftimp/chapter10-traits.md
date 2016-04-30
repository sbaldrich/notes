## Chapter 10: Traits

### Keypoints:
  * Construction order when using traits is **important**. See ABN for this chapter.
  * It is possible to have a trait that extends from a class. (This is a mess but I guess it can be useful sometimes (?)). Also, it is possible to use *Self types*. See ABN

### Exercises:

> (1) The `java.awt.Rectangle` class has useful methods translate and grow that are unfortunately absent from classes such as `java.awt.geom.Ellipse2D`. In Scala, you can fix this problem. Define a trait `RectangleLike` with concrete methods `translate` and `grow`. Provide any abstract methods that you need for the implementation, so that you can mix in the trait like `val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike`.

```scala
trait RectangleLike {
	this : java.awt.geom.Ellipse2D.Double =>
	def translate(dx : Int, dy : Int) { setFrame(getX + dx, getY + dy, getWidth, getHeight) }
	def grow(h : Int, v : Int) { setFrame(getX, getY, getWidth + v, getHeight + h) }
}
```
> (2) Define a class `OrderedPoint` by mixing `scala.math.Ordered[Point]` into `java.awt.Point`. Use lexicographic ordering, i.e. (x, y) < (x’, y’) if x < x’ or x = x’ and y < y’.

```scala
import scala.math.Ordered
import java.awt.Point

class OrderedPoint(x: Int, y:Int) extends Point(x,y) with Ordered[Point]{
	def compare(that : Point) = {
		if(this.x < that.x) -1
		else if(this.x == that.x && this.y < that.y) -1
		else if(this.x == that.x && this.x == that.x) 0
		else 1
	}
}
```
> (4) Provide a `CryptoLogger` trait that encrypts the log messages with the Caesar cipher. The key should be 3 by default, but it should be overridable by the user. Provide usage examples with the default key and a key of –3.

```scala
trait Logger{
  def log(msg : String) = {}
}

trait SimpleLogger extends Logger{
  override def log(msg : String){ Console.println(msg) }
}

trait CryptoLogger extends Logger{
  val shift = 3
  override def log(msg : String) { super.log( encript(msg) ) }
  def encript(message : String) = message.map(caesar(_).toChar).mkString
  def caesar(char : Char) = {
    val ret = (char.toInt - 'a') + shift
    'a' + (if(ret < 0) 26 + ret % 26 else ret % 26)
  }
}

class Example extends Logger{
  def fun(message : String) = log(message)
}

(new Example with SimpleLogger with CryptoLogger).fun("abcde") //defgh
(new Example with SimpleLogger with CryptoLogger {override val shift = -3}).fun("abcde") //xyzab
```



### A Brief note on Traits

##### Construction order

The construction order of classes and traits is dictated by a process called *Linearization* (it is, look it up). Basically, the superclass is first constructed, then each trait is constructed in a left to right parent-first order. This means that if a trait has a parent, it is constructed (if it hasn't been constructed before) and then the trait itself is constructed. Consider this:

```scala
import java.io.PrintStream

trait Logger{
  def log(msg : String)
}

trait FileLogger extends Logger{
  val filename : String // abstract field
  val out = new PrintStream(filename)
  override def log(msg : String){
    out.println(msg)
    out.flush()
  }
}

class Account(var balance : Double = 0.0)

val acct = new Account with FileLogger{ val filename = "output.log" } //Boom
```
That last line will fail because of the previously mentioned construction order *i.e.* the filename variable hasn't been initialized. Use `lazy` values (not recommended since it is less efficient) or early definition.

##### Self types

A trait can specify the types with which it can be mixed in.

```scala
trait LoggedException extends Logger{
  this : Exception =>  //Can only be mixed with subtypes of exception
    def log() {log(getMessage())} //Calling the getMessage method from Exception
}

trait Talker{
  this : { def getMessage : String } =>{
    def saySomething {println(getMessage)}
  }
}
```
