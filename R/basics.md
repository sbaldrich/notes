## Notes on the basics of R
1. [Pro-tips](#protips)
1. [Subsetting and sorting data frames](#dataframe)
1. [Functions](#functions)
	1. [Loop Functions](#lfunctions) 
1. [data.table](#datatable)
1. [Miscellaneous](#misc)

<a name="protips"></a>
### Pro-tips

* Always set the seed when creating a script with reproducible results.

<a name="dataframe"></a>
### Subsetting and Sorting

```R
set.seed(13435)
X <- data.frame("var1"=sample(1:5),"var2"=sample(6:10),"var3"=sample(11:15))
X <- X[sample(1:5),];X$var2[c(1,3)] = NA
X

##   var1 var2 var3
## 1    2   NA   15
## 4    1   10   11
## 2    3   NA   12
## 3    5    6   14
## 5    4    9   13

X[,1] ## Get the first column

## [1] 2 1 3 5 4

X[,"var1"] ## Get the "var1" column

## [1] 2 1 3 5 4

X[1:2, "var2"] ## Get the first and second row for the "var2" variable

## [1] NA 10

X[(X$var1 <= 3 & X$var3 > 11),]

##   var1 var2 var3
## 1    2   NA   15
## 2    3   NA   12

X[(X$var1 <= 3 | X$var3 > 15),]

##   var1 var2 var3
## 1    2   NA   15
## 4    1   10   11
## 2    3   NA   12

X[which(X$var2 > 8 ),] ## Subset dealing correctly with NA values

##   var1 var2 var3
## 4    1   10   11
## 5    4    9   13

sort(X$var2, na.last = T)

## [1]  6  9 10 NA NA

X[order(X$var1,X$var3),]

##   var1 var2 var3
## 4    1   10   11
## 1    2   NA   15
## 2    3   NA   12
## 5    4    9   13
## 3    5    6   14

Y <- cbind(X, rnorm(5)) ## Append a new column. Invert the parameter order for prepend.

##   var1 var2 var3    rnorm(5)
## 1    2   NA   15  0.62578490
## 4    1   10   11 -2.45083750
## 2    3   NA   12  0.08909424
## 3    5    6   14  0.47838570
## 5    4    9   13  1.00053336

Y <- rbind(X, 1:3) ## Append a new row. Invert the parameter order for prepend. 

##   var1 var2 var3
## 1    2   NA   15
## 4    1   10   11
## 2    3   NA   12
## 3    5    6   14
## 5    4    9   13
## 6    1    2    3

```

<a name="functions"></a>
### Functions

In R, functions are defined by using the `function` directive and are **first class objects**. The return value of the function is the value of the last expression in the function body.

```R
f <- function(arg1 = NULL, arg2 = 10){ ## Default values and named parameters are ok to use
  ## Something really cool
}
```

Arguments to functions are evaluated **lazily**:

```R
g <- function(x,y){
	x^2
}

g(2) ## This won't produce any error since y was never needed.
```

Use an ellipsis whenever the number of arguments cannot be known in advance:

```R
args(paste)
function(..., sep= " ", collapse = NULL) ## Notice that args after the ... must be named on function calls.
```

<a name="lfunctions"></a>
#### Loop functions

##### `lapply` and `sapply`
Apply a function to all elements in a list and return the result. This result depends on the `*apply` function that is used. For example, `lapply` returns a vector with the computed values and `sapply` simplifies the results if possible *i.e.,* if the result of applying the given function to each element of the input list always returns a vector of size 1, `sapply` returns a vector with the results. If the function returns a set of vectors of the same size, a matrix is returned.

```R
x <- list(a = 1:5, b=rnorm(10))
lapply(x,mean)
## $a
## [1] 3
## $b
## [2] -0.04666781

x <- list(a=matrix(1:4,2,2), b=matrix(3:6,3,2))
sapply(x, function(y) y[1,]) #An anonymous function that extracts the first row
##      a b
## [1,] 1 1
## [2,] 3 4
```

##### `apply`
Apply a function over some dimensions (a margin) of a vector or matrix. It can be used with more than 2 dimensions.

```R
x <- matrix(1:20, 5, 4)
x
##      [,1] [,2] [,3] [,4]
## [1,]    1    6   11   16
## [2,]    2    7   12   17
## [3,]    3    8   13   18
## [4,]    4    9   14   19
## [5,]    5   10   15   20
apply(x, 1, mean)
## [1]  8.5  9.5 10.5 11.5 12.5
apply(x, 2, mean)
## [1]  3  8 13 18

rowSums(x) #apply(x, 1, sum)
rowMeans(x) #apply(x, 1, mean)
colSums(x) #apply(x, 2, sum)
colMeans(x) #apply(x, 2, mean)

x <- matrix(rnorm(200), 20, 10)
apply(x, 1, quantile, c(0.25, 0.5, 0.75))
## [,1]       [,2]       [,3]
## 25% -0.43974872 -0.6027969 -0.8987208
## 50%  0.09358884  0.3069260 -0.3751820
## 75%  0.48934699  0.6348676  0.1333773
## [,4]       [,5]       [,6]
## 25% -0.2098863 -0.7077898 -0.7406839
## 50% -0.1325445  0.1561425 -0.5986744
## 75%  0.3398246  0.7423683 -0.2781488
## [,7]       [,8]        [,9]
## 25% -0.01816284 -0.7556663 -0.35393989
## 50%  0.46696873  0.3613250 -0.01122934
## 75%  0.95474605  0.5916517  0.92885438
## [,10]       [,11]       [,12]
## 25% -0.2468998 -0.05331366 -0.07723868
## 50%  0.7201212  0.23441194  0.28756834
## ...
```

##### `mapply`

Multivariate apply. Uses the arguments of multiple lists to call a function.

```R
mapply(sum, 1:3, 4:7)
## [1] 5 7 9

mapply(rnorm, 1:3, 1:3, 2)
## [[1]]
## [1] 7.138163
##
## [[2]]
## [1] 0.4587329 3.2667744
##
## [[3]]
## [1] 0.6485012 3.7339094 1.6486525
```

##### `tapply`

Apply a function over subsets of a vector.

```R
x <- c(rnorm(10), runif(10), rnorm(10,1))
f <- gl(3, 10)
f
## [1] 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2 3 3 3 3 3 3 3
## [28] 3 3 3
## Levels: 1 2 3
tapply(x, f, mean)
##          1          2          3 
## -0.4175986  0.6728019  1.2356494 
```

##### `split`

Split a vector into subsets determined by a factor or a list of factors.

```R
x <- c(rnorm(3), runif(3), rnorm(3,1))
f <- gl(3, 3)
split(x, f)
## $`1`
## [1] -0.4379933  0.6602717  1.0666278
## 
## $`2`
## [1] 0.5800965 0.3642840 0.8716409
## 
## $`3`
## [1] -0.2689952 -0.3325070  3.9370044


library(datasets)
head(airquality)
##   Ozone Solar.R Wind Temp Month Day
## 1    41     190  7.4   67     5   1
## 2    36     118  8.0   72     5   2
## 3    12     149 12.6   74     5   3
## 4    18     313 11.5   62     5   4
## 5    NA      NA 14.3   56     5   5
## 6    28      NA 14.9   66     5   6
sapply(s, function(x) colMeans(x[, c("Ozone", "Solar.R", "Wind")], na.rm = TRUE))
##                 5         6          7          8         9
## Ozone    23.61538  29.44444  59.115385  59.961538  31.44828
## Solar.R 181.29630 190.16667 216.483871 171.857143 167.43333
## Wind     11.62258  10.26667   8.941935   8.793548  10.18000
```

<a name="datatable"></a>
### data.table

`data.table` inherits from `data.frame`, is written in C and is much faster. They can be created just like data frames:

```R
library(data.table)
DF = data.frame(x=rnorm(9), y = rep(c("a","b","c"), each=3), z = rnorm(9))

##            x y          z
## 1  2.2011118 a -2.3554687
## 2 -0.5477701 a  1.7970621
## 3 -1.1049003 a -0.2485105

DT = data.table(x=rnorm(9), y = rep(c("a","b","c"), each=3), z = rnorm(9))
head(DT,3)

##              x y            z
## 1:  1.91827920 a  1.856464505
## 2: -1.65494906 a  0.002909822
## 3: -0.01420802 a -1.219338098
```

##### Subsetting rows:

```R
DT[2,]

##            x y           z
## 1: -1.654949 a 0.002909822

DT[DT$y=="a",]

##              x y            z
## 1:  1.91827920 a  1.856464505
## 2: -1.65494906 a  0.002909822
## 3: -0.01420802 a -1.219338098

DT[c(2,3)] ## Subsetting using only one index uses rows

##              x y            z
## 1: -1.65494906 a  0.002909822
## 2: -0.01420802 a -1.219338098
```

##### Subsetting colums:

Use expressions (statements between curly brackets) to subset columns:

```R
DT[, list(mean(x),sum(z))] ## The data table knows which variables are refered

##           V1        V2
## 1: -0.221905 -2.110566

DT[,table(y)]

## y
## a b c 
## 3 3 3

```

##### Append colums:

Use `:=` to add a new variable to a `data.table`. Using a `data.frame`, a copy of the full data frame would be made and the new variable would be appended to it. The `data.table` **does not make a copy when appending!**

```R
DT[,w:=x^2]

##              x y            z            w
## 1:  1.91827920 a  1.856464505 3.6797951076
## 2: -1.65494906 a  0.002909822 2.7388564037
## 3: -0.01420802 a -1.219338098 0.0002018679

DT[,m := {tmp <- (x+z); log2(tmp+5)}] ## Use expressions to append more complex variables
head(DT,3)

##              x y            z            w        m
## 1:  1.91827920 a  1.856464505 3.6797951076 3.133357
## 2: -1.65494906 a  0.002909822 2.7388564037 1.743283
## 3: -0.01420802 a -1.219338098 0.0002018679 1.913207
```

<a name="misc"></a>
### `str` function

Compactly display the inner structure of R objects.

```R
x <- rnorm(100, 10)
str(x)

## num [1:100] 8.38 9.09 9.08 10.15 9.59 ...

library(datasets)
str(airquality)

## 'data.frame':	153 obs. of  6 variables:
## $ Ozone  : int  41 36 12 18 NA 28 23 19 8 NA ...
## $ Solar.R: int  190 118 149 313 NA NA 299 99 19 194 ...
## $ Wind   : num  7.4 8 12.6 11.5 14.3 14.9 8.6 13.8 20.1 8.6 ...
## $ Temp   : int  67 72 74 62 56 66 65 59 61 69 ...
## $ Month  : int  5 5 5 5 5 5 5 5 5 5 ...
## $ Day    : int  1 2 3 4 5 6 7 8 9 10 ...

m <- matrix(rnorm(100), 10, 10)
str(m)
## num [1:10, 1:10] -0.0393 1.1132 2.1891 -0.1054 0.3433 ...

```

### Simulation

#### Generating Random Numbers

##### Functions for probability distributions

Probability distributions have four associated functions prefixed with:

* `d`: density
* `r`: random number generation
* `p`: cumulative distribution
* `q`: quantile

*e.g.,* `dnorm, rnorm, pnorm, qnorm, rpois, qpois`

*Remember to always set the seed (`set.seed`) to ensure reproducibility*

##### Random Sampling

Use `sample` to randomly draw from a set scalar objects.

```R
set.seed(10)
sample(1:10, 4)
##  6 3 4 5

sample(1:10, replace=TRUE)
##  1 3 3 3 7 5 7 6 2 6
```

### Getting and cleaning data

Some useful functions for getting data:

* `file.download`: Download a file from a given url. (use `method="curl"` on OSX, sometimes "wb" is necessary on Windows, see *getdata/quiz1.md*)
* `read.csv`: Read a csv file.
* `library(xlsx)`: Utilities for reading Excel files. (See *getdata/quiz1.md*)
* `library(XML)`: Utilities for reading and processing XML files. `xmlSApply` and `xpathSApply` are particularly useful.


### Summarizing data

There are useful functions for summarizing data before doing any operations with it:

* `head` and `tail` work as the bash commands. They also receive an `n` parameter.
* `summary` makes a summary (woah) of the data.
* `str` gives a structured representation of an R object.
* `quantile` is obvious. A `probs` parameter can be given to change the percentiles to be returned.
* `table` creates a table with the frequency of each value in an object. Use `useNA="ifany"` to take into account the NA values. Two-dimensional tables can also be created by passing a second variable.
* `is.na` allows to check for NA values.
* Use `any` as you would in Python. `all` can also be used.
* `colSums` and `rowSums` do exactly what their names suggest.
* Use `%in%` to check whether elements in one vector appear in another one.
* Use `object.size` and `print(object.size(obj), units="Mb")` to see the size of (you guessed it) an object.
