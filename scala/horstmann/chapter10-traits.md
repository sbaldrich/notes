## Chapter 10: Traits

### Keypoints:
  * Construction order when using traits is **important**. See ABN for this chapter.
  * It is possible to have a trait that extends from a class. (This is a mess but I guess it can be useful sometimes (?)). Also, it is possible to use *Self types*. See ABN

### Exercises:

1.
```scala
trait RectangleLike {
	this : java.awt.geom.Ellipse2D.Double =>
	def translate(dx : Int, dy : Int) { setFrame(getX + dx, getY + dy, getWidth, getHeight) }
	def grow(h : Int, v : Int) { setFrame(getX, getY, getWidth + v, getHeight + h) }
}
```

2. 
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
4.
```scala
trait Logger{
	def log(msg : String) = {}
}

trait SimpleLogger extends Logger{
	override def log(msg : String) = {
		Console.println(msg)
	}
}

trait CryptoLogger extends Logger{
	override def log(msg : String) = { super.log(encript(msg)) }
	def encript(message : String) = {
		Console.println("inside crypto")
		val shift = 3
		message.map(x => (x + shift).toChar)
	}
}

class Example extends Logger{
	def fun() = log("abcdefghijklmnopqrstuvwxyz")
}

val example = new Example with SimpleLogger
example.fun
val cryptoExample = new Example with SimpleLogger with CryptoLogger
cryptoExample.fun
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