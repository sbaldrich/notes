## Chapter 9: Files and Regular Expressions

* `scala.io.Source` objects can be used to read files.
* "regex".r is a `Regex` object.
* Call `close` when you're done with the `Source` object.
* Use """...""" for raw regex syntax.
* Use `@SerialVersionUID(aLong)` and `extends Serializable` to enable serialization.

### Exercises

> (1) Write a Scala code snippet that reverses the lines in a file (making the last line the first one, and so on).

```scala
import scala.io.Source

print(Source.fromFile("lipsum.txt").getLines.toArray.reverse.mkString("\n"))
```
> (3) Write a Scala code snippet that reads a file and prints all words with more than 12 characters to the console. Extra credit if you can do this in a single line.

```scala
Source.fromFile("lipsum.txt").mkString.replaceAll("[^\\w ]", "").split("\\s+").filter(_.length > 12).map(println(_))
```

> (7) Write a Scala program that reads a text file and prints all tokens in the file that are not floating-point numbers. Use a regular expression.

```scala
val regex = """[^\d+.\d+]""".r
Source.fromFile("lipsum.txt").mkString.split("\\s+").filter(regex.findFirstIn(_) != None).map(println(_))
```
> (8) Write a Scala program that prints the src attributes of all img tags of a web page. Use regular expressions and groups.

```scala
val regex = """<img.*src="(.*)".*>""".r
val string = Source.fromURL("http://www.nytimes.com", "UTF-8").mkString
for(regex(attr) <- regex.findAllIn(string)) println(attr)
```
