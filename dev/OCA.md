## Study notes for the OCA Exam
1. [Basics](#basics)
2. [Operators and Statements](#operators)

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
