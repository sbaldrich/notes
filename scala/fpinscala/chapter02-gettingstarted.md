## Chapter 2: Getting Started

### Exercises

>(1) Implement a tail-recursive version of a function to find the n-th Fibonacci number

```scala
def fib(n : Int) : Int = {
  def loop(n: Int, prev : Int, cur : Int) : Int =
    if (n == 0) prev else loop( n-1, cur, prev + cur)
  loop(n, 0, 1)
}
```

>(2) Implement `isSorted` which checks whether a given array is sorted according to a comparison function

```scala
def isSorted[A](as : Array[A], ordered : (A,A) => Boolean) : Boolean = {
  def loop(n : Int) : Boolean = {
    if(n >= as.length - 1) true
    else if( ordered(as(n), as(n + 1)) ) loop(n + 1)
    else false
  }
  loop(0)
}
```

>(3) Implement this `curry`

```scala
def curry[A,B,C](f: (A, B) => C): A => (B => C) = (a : A) => (b : B) => f(a, b)
```

>(4) Now `uncurry`

```scala
def uncurry[A,B,C](f: A => B => C): (A, B) => C = (a : A, b : B) => f(a)(b)
```

>(5) Implement function composition

```scala
def compose[A,B,C](f: B => C, g: A => B): A => C = a => f(g(a))
```
