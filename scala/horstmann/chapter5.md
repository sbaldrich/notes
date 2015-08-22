## Chapter 5

### Keypoints:
  * The visibility of the mutator and accesor methods generated (and whether they are generated) is controlled by the use of the `private`, `val`, `var` and `private[this]`.
  * Default arguments are ok.
  * Use @BeanProperty to generate the usual JavaBean methods.
  * Nested classes are possible, take a look at the bottom of this file.


```scala
class Counter(private var value : Int = 0){

  def increment(){
    if(value < Integer.MAX_VALUE)
      value += 1
  }

  def current = value  
}
```

```scala
class BankAccount(private var balance : Double = 0.0){
  def deposit(amount : Double) {
    balance += amount
  }
  def withdraw(amount : Double) = {
    if(balance >= amount)
      balance -= amount
  }

  def currentBalance = balance
}
```

```scala
class Time(val hours : Int, val minutes : Int){
  def before(other : Time) = {
    hours < other.hours || (hours == other.hours && minutes < other.minutes)
  }
}
```

```scala
class Time(hours : Int, minutes : Int){
  val minsSinceMidnight = hours * 60 + minutes;

  def before(other : Time) = {
    minsSinceMidnight < other.minsSinceMidnight
  }
}
```


```scala
import scala.reflect.BeanProperty

class Student(@BeanProperty var name : String, @BeanProperty var id : Long)
```

```scala
class Person(var age : Int){
  age = if (age < 0) 0 else age
}
```
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
