## Study notes for the OCA Exam
1. [Basics](#basics)
2. [Operators and Statements](#operators)
2. [Core Java APIs](#core)

<a name="basics"></a>
### Basics (*the java building blocks*)

* More than one class can be defined in the same source file (*don't do this*) as long as there is at most **one** public class *(really, don't do this)*.
* You can use *varargs* in the `main` method's signature.
* Watch out for ambiguous imports **e.g.,** importing `java.util.Date` and `java.sql.Date`.
  * Java resolves the conflicts by honoring the most specific import (when possible).
* **Initialization order**: Field initializers and instance initializers in the order they are found and then the constructor.
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