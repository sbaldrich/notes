## Chapter 13: Collections

### Keypoints:

* Remember that any operator finishing in `:` is right associative and must be a method of the right operand.
* Create `List`s with the `::` and `:::` operators and `Stream`s using the `#::` operator. Read ABN for this chapter.

### A Brief Note on Streams

```scala
def numsFrom( n : BigInt ) : Stream[BigInt] = n #:: numsFrom(n + 1)
```
