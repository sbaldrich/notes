## Help!
1. [Maven](#maven)

<a name="maven"></a>
### Maven

#### `mvn compile` says package doesn't exist, but eclipse sees it!


**Problem**: A project in eclipse runs correctly but when I try to run it from terminal it says there are missing packages.

**Solution**: It is possible that the packages that are missing correspond to runtime dependencies of the project. Explicitly
adding the relevant dependencies to the pom file should solve the issue.

>The missing packages are runtime dependencies, which is why compilation from the command line fails. However, the Maven Eclipse plugin tends to ignore scoping rules and just pull every dependendency, which is why compilation from Eclipse will work without problems.
See [StackOverflow](http://stackoverflow.com/questions/17250741/maven-compile-package-does-not-exist#comment25015851_17251659).

#### `mvn test` says package doesn't exist, but eclipse sees it!

**Problem**: A project in eclipse can be tested correctly but when I try to test it from terminal it says there are missing packages.

**Solution**: Have you by any chance overwritten the <sourceDirectory> property in the pom.xml file?. If your test code is inside the same directory than the non-test code, you may face this kind of problems.

> You shouldn't override your `<sourceDirectory>` setting in the POM's `<build>` element unless you have a good reason to. This attribute determines where Maven looks for non-test code. The default value for this attribute is `src/main/java`. The `<testSourceDirectory>` attribute sets the path to test code (this defaults to src/test/java. By setting the `<sourceDirectory>` to simply src, Maven considers that entire directory to contain main application code. Since the src directory contains src/test/java, Maven then tries to compile your test code as part of the main application. See [StackOverflow](http://stackoverflow.com/a/15029935/1898695).

