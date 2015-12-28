## Notes on regular expressions
1. [Uppercase](#toupper)
1. [Non-capturing groups](#non-capturinggroups)

<a name="toupper"></a>
### Convert to upper case
I tested this particular regex on `vim`. I had to change some strings with the format `[a-z]+.toUpperCase()` with the string in question in upper case.
```vim
%s/,"\(.*\)".toUpperCase()/,\U"\1"/g
```
[Link related](https://coderwall.com/p/anvddw/vim-convert-text-to-lowercase-or-uppercase)

<a name="non-capturinggroups"></a>
### Using non-capturing groups
Sometimes it is necessary to choose between strings in a particular regex. Using parentheses would be useful but this creates a capturing group. To avoid it use the `(?:<<ex1|ex2|...|exN)` syntax. For example, `(?:black|straw)berry` would match *blackberry* and *strawberry*.
