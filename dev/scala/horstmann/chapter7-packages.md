## Chapter 7

### Keypoints:
  * Packages are open-ended, so everyone can contribute to a package at any time. Avoid conflicts using fully qualified names.
  * Package statements can be nested and everything in the parent package is in scope.
  * If a package statement is on the top of the file with no braces, it applies to all classes in the source file.
  * Use package objects to define methods, classes and objects that shouldn't depend on a particular class
  * Package visibility is achieved by using a `private[packageName]` qualifier.
  * Wildcard imports work as a java `import package.*` plus `import static`
  * It is possible to rename or hide fields. See the brief note for this chapter.

> (3) Write a package random with functions nextInt(): Int, nextDouble(): Double, and setSeed(seed: Int): Unit. To generate random numbers, use the linear congruential generator
`next = (previous × a + b) mod 2^n`,
where `a = 1664525`, `b = 1013904223`, `n = 32`, and the initial value of previous is seed.

```scala
package object random {
	private var next = 1
  def nextInt(): Int = {
    next = (next * 1664525 + 1013904223) % math.pow(2, 32).toInt
    next
  }

  def nextDouble(): Double = nextInt

  def setSeed(seed: Int){
    next = seed
  }
}

package random{}

```

### A brief note on hiding and renaming imports

Importing a few members from a package can be done by using a special selector syntax. Additionally, imports can be renamed or
hidden to avoid conflicts.

```scala
import java.util.{HashMap,HashSet} // Import only `HashMap` and `HashSet`

import java.util.{HashMap => JavaHashMap} //Import HashMap and refer to it as JavaHashMap

import java.util.{HashMap => _ } // Hide the Java HashMap type.
import collection.mutable._      // Now HashMap refers unambigously to the Scala type.
```
