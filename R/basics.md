## Loop functions

### `lapply` and `sapply`
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

### `apply`
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
## 75%  1.1693685  1.03758764  0.80338771
## [,13]      [,14]         [,15]
## 25% -0.5166430 -1.1257728 -1.5978577112
## 50%  0.7758219 -0.2901696  0.0002375202
## 75%  1.4961718  0.3093578  0.7169169273
## [,16]       [,17]       [,18]
## 25% -0.7802628 -0.94697621 -1.29861844
## 50%  0.1956188  0.06379559 -0.50877237
## 75%  0.6749682  1.14786492  0.01926205
## [,19]      [,20]
## 25% -0.04754956 -1.2580690
## 50%  0.71227253 -0.3729704
## 75%  0.95471290  0.2537855
```

### `mapply`

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

### `tapply`

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

### `split`

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

## `str` function

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

## Simulation

### Generating Random Numbers

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