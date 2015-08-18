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
