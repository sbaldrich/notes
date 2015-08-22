## Chapter 6

### Keypoints:
  * Scala has no static methods or fields. Enter `object`.
  * You can get "static methods and fields" by using "companion objects", *i.e*,
    a `class` and an an `object` with the same name located in **the same source file**
  * Objects may extend from classes and traits.
  * Often, Objects use the `apply` method to construct instances of the companion class
  * There are no plain `enums` in Scala, use an object that extends `Enumeration` and
    initialize the values with a call to `Value`. *See the bottom of the page*.

```scala
object Conversions{
  def inchesToCentimeters( in : Double ) = in * 2.54

  def gallonToLiters( gal : Double ) = gal * 3.7854

  def milesToKilometers( mil : Double ) = mil * 1.6;
}
```
```scala
abstract class UnitConversion{
  def convert( in : Double ) : Double
}

object InchesToCentimeters extends UnitConversion{
  def convert( in : Double ) = in * 2.54
}

object GallonToLiters extends UnitConversion{
  def convert( in : Double ) = in * 3.7854
}

object MilesToKilometers extends UnitConversion{
  def convert( in : Double ) = in * 1.6
}
```
```scala
class Point private(val x : Double, val y : Double){}

object Point{
  def apply(x : Double, y : Double) = new Point(x,y)
}
```
```scala
object Reverse extends App{
  println(args.reverse.mkString(" "))
}
```
```scala
object Suit extends Enumeration{
  val Club = Value("♣")
  val Diamond = Value("♦")
  val Heart = Value("♥")
  val Spade = Value("♠")
}
```
```scala
def isRedSuit( suit : Suit.Value ) = suit.id == Suit.Heart.id || suit.id == Suit.Diamond.id
```
```scala
object RGBCorners extends Enumeration {
    val Black = Value(0x000000)
    val Red = Value(0xff0000)
    val Green = Value(0x00ff00)
    val Blue = Value(0x0000ff)
    val Yellow = Value(0xffff00)
    val Cyan = Value(0x00ffff)
    val Magenta = Value(0xff00ff)
    val White = Value(0xffffff)
}
```

### A brief note on `Enumeration`s

Take for example an enumeration for the possible states of a Circuit Breaker.

```scala
object Status extends Enumeration{
  val OPEN, HALF_OPEN, CLOSED = Value
}
```


The type of the enumeration **is not** `Status` it is `Status.Value`. It is possible to use a type alias to make the use of the enumeration with imports a little easier for the eyes.
