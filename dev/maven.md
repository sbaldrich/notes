## Notes about Maven
1. [Copying dependencies](#deps)
1. [Jetty plugin](#jetty)

<a name="deps"></a>
### Copying dependencies of a project

To get all dependencies of a project, use the `copy-dependencies` goal of the `dependency` plugin. To get the same directory structure as in the *m2* repo, use `-Dmdep.useRepositoryLayout` and to copy pom files too, use `-Dmdep.copyPom`

**e.g.:** `mvn clean dependency:copy-dependencies -Dmdep.useRepositoryLayout -Dmdep.copyPom`

<a name="jetty"></a>
### Using the Maven Jetty Plugin

##### Set up

Add the `jetty-maven-plugin` to the `pom.xml` file. Take care of using a version that is actually on the maven central repository, there is [documentation](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html#configuring-security-settings) that lists an incorrect version.

```xml
<properties>
...
<jetty.version>9.3.1.v20150714</jetty.version> <!-- I was able to work with this version-->
...
</properties>
<plugin>
	<groupId>org.eclipse.jetty</groupId>
	<artifactId>jetty-maven-plugin</artifactId>
	<version>${jetty.version}</version>
	<configuration>
		<webAppSourceDirectory>web</webAppSourceDirectory> <!-- The default is src/main/webapp -->
	</configuration>
</plugin>
```

Done! now use the the `jetty:run` goal to start the server.

##### Set up a DataSource

Create a `jetty-env.xml` file and add it to the `WEB-INF` folder, in this file, various JNDI configurations can be specified.
Add the configuration of the datasource you want, for example:

```xml
<?xml version="1.0"?>
<!DOCTYPE Configure PUBLIC "-//Mort Bay Consulting//DTD Configure//EN" "http://www.eclipse.org/jetty/configure_9_0.dtd">

<Configure class="org.eclipse.jetty.webapp.WebAppContext">

	<!-- an XADataSource -->
	<New id="catchands" class="org.eclipse.jetty.plus.jndi.Resource">
		<Arg></Arg>
		<Arg>jdbc/catchan</Arg>
		<Arg>
			<New class="org.postgresql.ds.PGSimpleDataSource">
				<Set name="User">sbaldrich</Set>
				<Set name="Password"></Set>
				<Set name="DatabaseName">catchan</Set>
				<Set name="ServerName">localhost</Set>
				<Set name="PortNumber">5432</Set>
			</New>
		</Arg>
	</New>

</Configure>
```
The previous listing configures a PostgreSQL datasource and provides the authorization credentials. Information on how to configure other datasources can be found [here](http://wiki.eclipse.org/Jetty/Howto/Configure_JNDI_Datasource).

##### References

* [Official documentation](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html)
* [Jetty/Howto/Configure JNDI Datasource](http://wiki.eclipse.org/Jetty/Howto/Configure_JNDI_Datasource)
