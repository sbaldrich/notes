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