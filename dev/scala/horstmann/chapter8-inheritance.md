## Chapter 8

### Keypoints:
  * Always use the `override` keyword to override methods that aren't abstract.
  * Fields can be overriden.
  * Use `obj.isInstanceOf[Class]`, `obj.asInstanceOf[Class]` and `classOf[Class]` to check whether `obj` is an instance or subclass and to get `obj` as a `Class`, which works only if indeed `obj` is a subclass of `Class`.
  * `protected` members are not visible throughout the package. `protected[this]` restricts access to the current object, in a similar way that `private[this]` does.

### Exercises

> (1) Extend the following `BankAccount` class to a `CheckingAccount` class that charges $1 for every deposit and withdrawal.

```scala
class BankAccount(initialBalance : Double){
  private var balance = initialBalance
  def currentBalance = balance
  def deposit(amount : Double) = {balance += amount; balance}
  def withdraw(amount : Double) = {balance -= amount; balance}
}

class CheckingAccount(initialBalance : Double = 0.0) extends BankAccount(initialBalance){
  val cost = 1
  override def deposit(amount : Double) = {super.deposit(amount - cost); currentBalance}
  override def withdraw(amount : Double) = {super.withdraw(amount + cost); currentBalance}
}
```

> (2) Extend the BankAccount class of the preceding exercise into a class SavingsAccount that earns interest every month (when a method earnMonthlyInterest is called) and has three free deposits or withdrawals every month. Reset the transaction count in the earnMonthlyInterest method.

```scala
class SavingsAccount(initialBalance : Double) extends BankAccount(initialBalance){

  var operations = 0

  def charge = {
    operations += 1
    if(operations > 3) 1 else 0
  }

  override def deposit(amount : Double) = {
    super.deposit(amount - charge)
  }
  override def withdraw(amount : Double) = {
    super.withdraw(amount + charge)

  def earnMonthlyInterest() { operations = 0 }
}
```
> (4) Define an abstract class `Item` with methods `price` and `description`. A `SimpleItem` is an item whose `price` and `description` are specified in the constructor. Take advantage of the fact that a `val` can override a `def`. A `Bundle` is an item that contains other items. Its price is the sum of the prices in the bundle. Also provide a mechanism for adding items to the bundle and a suitable description method.

```scala
abstract class Item{
  def price : Int
  def description : String
}

class SimpleItem private(override val price : Int, override val description : String) extends Item

import collection.mutable.ArrayBuffer

class Bundle extends Item{
  override def price = items.map(_.price).sum
  override def description = "{" + items.map(_.description).mkString(", ") + "}"

  private val items = ArrayBuffer[Item]()

  def add(item : Item) = { items += (item); this }
}
```
> (5) Design a class Point whose x and y coordinate values can be provided in a constructor. Provide a subclass `LabeledPoint` whose constructor takes a label value and x and y coordinates, such as `new LabeledPoint("Black Thursday", 1929, 230.07)`.

```scala
case class Point(val x : Double, val y : Double)


class LabeledPoint(val label : String, x : Double, y : Double) extends Point(x, y)
```

> (6) Define an abstract class `Shape` with an abstract method `centerPoint` and subclasses `Rectangle` and `Circle`. Provide appropriate constructors for the subclasses and override the `centerPoint` method in each subclass.

```scala

abstract class Shape{
  def centerPoint : Point
}

class Circle(val center : Point, val radius : Double ) extends Shape{
  def centerPoint = center
}

class Rectangle(val corner : Point, val height : Double, val width : Double) extends Shape{
  override def centerPoint = Point(corner.x + width / 2.0, corner.y - height / 2.0)
}
```
