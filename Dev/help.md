# Table of Contents
1. [Maven](#maven)

## Maven

### `mvn compile` says package doesn't exist, but eclipse sees it!


**Problem**: A project in eclipse runs correctly but when I try to run it from terminal it says there are missing packages.

**Solution**: It is possible that the packages that are missing correspond to runtime dependencies of the project. Explicitly
adding the relevant dependencies to the pom file should solve the issue.

>The missing packages are runtime dependencies, which is why compilation from the command line fails. However, the Maven Eclipse plugin tends to ignore scoping rules and just pull every dependendency, which is why compilation from Eclipse will work without problems
. See [StackOverflow](http://stackoverflow.com/questions/17250741/maven-compile-package-does-not-exist#comment25015851_17251659).
