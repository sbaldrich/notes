## Study notes for the OCA Exam
1. [Basics](#basics)
1. [Operators and Statements](#operators)
1. [Core Java APIs](#core)
1. [Methods and Encapsulation](#methods)
1. [Class Design](#class)

<a name="basics"></a>
### Basics (*the java building blocks*)

* More than one class can be defined in the same source file (*don't do this*) as long as there is at most **one** public class *(really, don't do this)*.
* You can use *varargs* in the `main` method's signature.
* Watch out for ambiguous imports **e.g.,** importing `java.util.Date` and `java.sql.Date`.
  * Java resolves the conflicts by honoring the most specific import (when possible).
* **Initialization order**: Superclass initialization, static variables declaration and static initializers in the order they are found, instance variables declararations and instance initializers in the order they are found and then the constructor.
* The underscores in numeric literals (Java 7+) can appear anywhere but in the start, end or next to a dot in a literal.
* `strictfp` and `native` are *keywords* you probably don't remember.
* Identifiers must start with a letter or the symbols `$` or `_`.
* The `finalize` method will be called **zero or one times!** and `System.gc()` merely suggests running garbage collection. 
* Wildcard package imports don't recurse into subpackages and the JVM only loads the classes that are actually needed.
*  **Benefits of Java** *(according to the OCA study book)*:
   * OOP
   * Encapsulation
   * Robustness
   * Platform independence
   * Simplicity

**Exam tip:** if the code listing doesn't start in line 1, assume all code that is not visible to be correct, **e.g.,** the import and package statements are there, the class definition is correct, etc. Always watch out for the `f` on `float` variables and the `L` on `long` variables.

<a name="operators"></a>
### Operators and Statements

* `short`, `byte` and `char` are always promoted to `int` when used with a **binary** operator.
* Integral types are always promoted to floating point when used with a binary operator and a fp variable.
* Unary operators don't force promotion.
* There's no logical inversion of numeric types in java (no !5)
* There's promotion but there's no demotion (`int x = 1.0` won't compile)
* Compound assignment performs a cast (if necessary) of the right hand argument to the type of the left-hand one.
* Promotion also works with comparison operators (`5 == 5.00` is `true`)
* `switch` statements support all the primitive numeric wrappers but `long`. `boolean` is also not accepted.
* Values in `case` statements must be compile-time constants (can be `final`) and of the same type as the `switch` variable.
* The order of the `case` statements is irrelevant.
* Labels can be used on any block statement (although why would you use a label with an `if` statement?)

**Exam tip:** Always verify whether the code compiles or not before looking for more complicated answers.

<a name="core"></a>
### Core Java APIs

* When using the `+` operator, all elements are evaluated from left to right and if either operand is a `String`, the result is a `String`, *e.g.,* `1 + 3 + "5" == "45"`.
* Literal `String`s go in the String pool.
* Always remember that `String`s are immutable.
* You can declare and initialize array anonymously: `int[] array = {1,2,3,4}` (see? no type on the right-hand side of the assignment).
* `java.util.Arrays.binarySearch` returns the negative of the index where the searched element should be inserted minus 1 whenever the searched value can't be found. Remember its behavior is unpredictable on unsorted arrays.
* The `remove` method behaves funny when invoked with an `int` because of *autoboxing*.
* The `asList()` method from `Arrays` returns a **backed list** (*when the array changes the list does too*), this means the size of the list cannot change among other things. It receives *varargs*, so `Arrays.asList("hello", "my", "darling")` is legal.
* `LocalDate`, `LocalTime` and `LocalDateTime` objects **should be created using the static methods `of` or `now`**, the constructors are `private`. `LocalDateTime` objects can be created using a `LocalDate` and `LocalTime`.
* Date and time classes are immutable.
* Give a look at the `java.time.*` classes, they're quite nice.
* Don't try to chain methods for `Period` construction. The `of...` methods are static so you'll end up with the result of the last call.
* The `DateTimeFormatter`'s `format` method can only be used if the date/time/datetime object contains the data relevant for the formatter (*don't use a time formatter with only a date*). Remember the datetime classes also contain a `format` that receives the formatter as argument.
* Create a date/time object from a `String` using the `parse` method and optionally a formatter.
* Calling `==` on `String` objects will check whether they point to the same object in the
String pool.

**Exam tip:** Write some programs to practice using the new Date and Time API.

<a name="methods"></a>
### Methods and Encapsulation

* There can be a maximum of one *vararg* parameter in a method.
* `protected` access is the same as the default access (package) plus the access by subclasses.
* A call to a static variable on a reference won't cause a `NullPointerException` even if the reference is currently `null`. Java uses the type of the reference to infer the variable we are refering to.
*  Java always calls the most specific method possible when it has more than one option, exact match is preferred, then wider primitives, then autoboxing and then *varargs*. Also, **only one conversion** is made, **e.g.,** an `int` won't be converted to `Long` in order to accomodate it to a method signature.
* `final` variables must be assigned a value exactly once. Even in a static initializer, the second assignment will fail. By the time the constructor completes, all `final` instance variables must have been set.
* Java 8 has a functional interface `Predicate<T>` that declares a method `test` that returns a `boolean`.
* If inside a constructor there is a call to another constructor, this call must be the first statement in the constructor.

**Exam tip:** Always identify whether fields and methods are *accessible* and watch out for non-properly encapsulated fields (**e.g.,** *returning the private reference*).

<a name="class"></a>
### Class Design

* A method overloaded by a *subclass* must be as accessible or more accessible than the same method in the *superclass*, it also cannot throw a checked exception that is new or broader than the class of any exception thrown by the method in the *superclass*. The return value must be the same or a subclass of the return type of the method in the *superclass*. The `static` modifier must be used consistently between the methods that are part of overriding.
* An overridden method may hide or eliminate exceptions without any problem.
* Keep in mind the concepts of *method overloading*, *method overriding* and *method hiding*.
* Variables can't be overriden, they are *hidden* (as static methods are).
* An `abstract` method may only be defined within an abstract class.
* A `private abstract` method can not be defined for somewhat obvious reasons.
* Every variable in an interface is assumed to be `public static final`.
* `default` method implementations are only allowed in interfaces.
* A default method can be overriden by an abstract method in a *subinterface*.
* Casts between types that are not related by an inheritance hierarchy are not allowed. An instance can be automatically cast to a superclass without explicit casting, to narrow an instance to a subclass, an explicit cast is required. A `ClassCastException` will be thrown if the instance does not correspond to the type is is being cast to.