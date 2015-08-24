## Chapter 8

### Keypoints:
  * Always use the `override` keyword to override methods that aren't abstract.
  * Fields can be overriden.
  * Use `obj.isInstanceOf[Class]`, `obj.asInstanceOf[Class] and `classOf[Class]` to check whether `obj` is an instance or subclass and to get `obj` as a `Class`, which works only if indeed `obj` is a subclass of `Class`.
  * `protected` members are not visible throughout the package. `protected[this]` restricts access to the current object, in a similar way that `private[this]` does.

```scala
class BankAccount(initialBalance : Double){
  private var balance = initialBalance
  def currentBalance = balance
  def deposit(amount : Double) = {balance += amount; balance}
  def withdraw(amount : Double) = {balance -= amount; balance}
}

class CheckingAccount(initialBalance : Double) extends BankAccount(initialBalance){
  override def deposit(amount : Double) = super.deposit(amount - 1)
  override def withdraw(amount : Double) = super.withdraw(amount + 1)
}
```

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
  }

  def earnMonthlyInterest() {operations = 0}
}
```

```scala
abstract class Item{
  def price : Int
  def description : String
}

class SimpleItem private(pr : Int, desc : String) extends Item{
  val price = pr
  val description = desc
}

object SimpleItem{
  def apply(pr : Int, desc : String) = new SimpleItem(pr, desc)
}

import collection.mutable.ArrayBuffer

class Bundle(val items : ArrayBuffer[Item] = new ArrayBuffer[Item]){
  def price = items.map(_.price).sum
  def description = items.map(_.description).mkString(", ")
}
```

```scala
class Point(val x : Int, val y : Int)

class LabeledPoint(val label : String, x : Int, y : Int) extends Point(x, y)
```

```scala
abstract class Shape{
  def centerPoint : Point
}

class Circle(val center : Point) extends Shape{
  def centerPoint = center
}
```
