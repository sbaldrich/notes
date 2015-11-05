##### Replacing a match with part of itself in uppercase

I had to change some strings with the format "[a-z]+.toUpperCase()" with the string in question in upper case. 

```vim
%s/,"\(.*\)".toUpperCase()/,\U"\1"/g
```

[Link related](https://coderwall.com/p/anvddw/vim-convert-text-to-lowercase-or-uppercase)

