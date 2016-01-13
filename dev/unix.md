# Notes on UNIX

1. [Bash](#bash)
1. [SSH](#ssh)

<a name="bash"></a>
## Bash

### Bang Bang

Some bang shortcuts:

* `!!`: execute the last command
* `!foo`: execute the last command that matches *foo*.
* `!foo:p`: print the last command that matches *foo* and put it at the top of the history.
* `!$`: run the last word of the previous command
* `^foo^bar`: run the previous command replacing *foo* with *bar*.

### Process substitution

Use process substitution to pipe the *stdout* of multiple commands or process the output of a command without creating a subshell.

```
while read line; do echo $line; done < <(ls -l /etc/init.d)
```
## ssh
<a name="ssh"></a>

### Setup an ssh key

`ssh-keygen -t rsa # setup an RSA keypair`

### Get the public keys from hosts and add them as known hosts

`ssh-keyscan host1 host2 anotherhost >> ~/.ssh/known_hosts`
