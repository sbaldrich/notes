## Chapter 9

### Files and Regular Expressions

* `scala.io.Source` objects can be used to read files.
* "regex".r is a `Regex` object.
* Call `close` when you're done with the `Source` object.
* Use """...""" for raw regex syntax.
* Use `@SerialVersionUID(aLong)` and extends Serializable to enable serialization.
#### Exercises

1.

```scala
import scala.io.Source

print(Source.fromFile("lipsum.txt").getLines.toArray.reverse.mkString("\n"))
```

3. 
```scala
Source.fromFile("lipsum.txt").mkString.replaceAll("[^\\w ]", "").split("\\s+").filter(_.length > 12).map(println(_))
```

7.
```scala
val regex = """[^\d+.\d+]""".r
Source.fromFile("lipsum.txt").mkString.split("\\s+").filter(regex.findFirstIn(_) != None).map(println(_))
```

8.
```scala
val regex = """<img.*src="(.*)".*>""".r
val string = Source.fromURL("http://www.nytimes.com", "UTF-8").mkString
for(regex(attr) <- regex.findAllIn(string)) println(attr)
```
