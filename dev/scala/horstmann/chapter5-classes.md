## Chapter 5

### Keypoints:
  * The visibility of the mutator and accesor methods generated (and whether they are generated) is controlled by the use of the `private`, `val`, `var` and `private[this]`.
  * Default arguments are ok.
  * Use `@BeanProperty` to generate the usual JavaBean methods.
  * Nested classes are possible, take a look at the bottom of this file.

  > (1) Improve the Counter class in Section 5.1, “Simple Classes and Parameterless Methods,” on page 49 so that it doesn’t turn negative at Int.MaxValue.

```scala
class Counter(private var value : Int = 0){

  def increment() {
    if(value < Integer.MAX_VALUE)
      value += 1
  }

  def current = value  
}
```
> (2) Write a class `BankAccount` with methods `deposit` and `withdraw`, and a read-only property `balance`.

```scala
class BankAccount(private var balance : Double = 0.0){
  def deposit(amount : Double) {
    balance += amount
  }

  def withdraw(amount : Double) = {
    if(balance >= amount && amount > 0)
      balance -= amount
  }

  def currentBalance = balance
}
```
> (3) Write a class `Time` with read-only properties `hours` and `minutes` and a method `before(other: Time): Boolean` that checks whether this time comes before the other. A `Time` object should be constructed as `new Time(hrs, min)`, where `hrs` is in military time format (between 0 and 23).

```scala
class Time(val hours : Int, val minutes : Int){
  def before(other : Time) = {
    hours < other.hours || (hours == other.hours && minutes < other.minutes)
  }
}
```
> (4) Reimplement the `Time` class from the preceding exercise so that the internal representation is the number of minutes since midnight (between 0 and 24 × 60 – 1). Do not change the public interface. That is, client code should be unaffected by your change.

```scala
class Time(h : Int, m : Int){
  val minsSinceMidnight = h * 60 + m

  def before(other : Time) = {
    minsSinceMidnight < other.minsSinceMidnight
  }

  def minutes = minsSinceMidnight % 60
  def hours = minsSinceMidnight / 60

}
```

> (5) Make a class `Student` with read-write JavaBeans properties name (of type `String`) and id (of type `Long`). What methods are generated? (Use `javap` to check.) Can you call the JavaBeans getters and setters in Scala? Should you?

```scala
import scala.reflect.BeanProperty
class Student(@BeanProperty var name : String, @BeanProperty var id : Long)
```

> (6) In the Person class of Section 5.1, “Simple Classes and Parameterless Methods,” on page 49, provide a primary constructor that turns negative ages to 0.

```scala
class Person(var age : Int){
  age = if (age < 0) 0 else age
}
```

> (7) Write a class Person with a primary constructor that accepts a `String` containing a first name, a space, and a last name, such as `new Person("Fred Smith")`. Supply read-only properties firstName and lastName. Should the primary constructor parameter be a var, a val, or a plain parameter? Why?

```scala
class Person(name : String){
  val firstName = name.split(" ")(0)
  val lastName = name.split(" ")(1)
}
```

### A brief note on nested classes

Say you want to build a social `Network` class with its corresponding `Member`.

```scala
import scala.collection.mutable.ArrayBuffer

// A network contains a set of members
class Network{
  class Member(val name : String){
    //Each member has a set of Members as contacts
    val contacts = new ArrayBuffer[Member]
  }

  private val members = new ArrayBuffer[Member]

  def join(name : String) = {
    val m = new Member(name)
    members += m
    m
  }
}
```

Now, consider a couple of networks

```scala
val twatter, footbook = new Network()
val mikael = twatter.join("mik")
val lisbeth = twatter.join("liz")

//Let Mikael follow Lisbeth on Twatter
mikael.contacts += lisbeth

val martin = footbook.join("martin")
// Martin tries to befriend Mikael
martin.contacts += mikael
/*
<console>:15: error: type mismatch;
 found   : twatter.Member
 required: footbook.Member
 		martin.contacts += mikael
 						 ^
*/
//But he can't.
```

Martin cannot befriend Mikael because although they are both `Member`s, Mikael is a `twatter.Member` and Martin `footbook.Member` (and a psycopath murderer, for that matter). This is, *each instance has its own class*.
