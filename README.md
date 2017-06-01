# BasicSql [![Build Status](https://img.shields.io/travis/FerusTech/BasicSql.svg)](https://travis-ci.org/FerusTech/BasicSql)
A utility for easily managing queries and executions to your database.

## Using BasicSql

View [Usage Examples](https://github.com/FerusTech/BasicSql/blob/master/basicsql-examples/src/main/java/tech/ferus/util/sql/examples/UsageExamples.java).

## Download
Latest Version: [![Maven Central](https://img.shields.io/maven-central/v/tech.ferus.util/BasicSql.svg)]()

Replace **TYPE** with `api`, `core`, `h2`, `mysql` or `sqlite`.

Replace **VERSION** with version shown in button above.

**Maven**
```xml
<dependency>
    <groupId>tech.ferus.util</groupId>
    <artifactId>basicsql-TYPE</artifactId>
    <version>VERSION</version>
</dependency>
```

**Gradle**
```groovy
repositories {
    mavenCentral()
}

dependencies {
    compile 'tech.ferus.util:basicsql-TYPE:VERSION'
}
```

## Javadocs
Can be located [here](https://ferustech.github.io/BasicSql/).

## Dependencies
BasicSql _requires_ **Java 8**.

Dependencies are managed automatically via Gradle. For a detailed list, view below.

| Name | Version | Website | Repository |
| ---- | ------- | ------- | ---------- |
| SLF4J-API | 1.7.21 | https://www.slf4j.org/ | https://github.com/qos-ch/slf4j |
| FindBugs | 3.0.2 | http://findbugs.sourceforge.net/ | https://github.com/findbugsproject/findbugs |
| Connector/J | 6.0.5 | https://www.mysql.com/products/connector/ | https://dev.mysql.com/downloads/connector/j/5.1.html |
| SQLite-JDBC | 3.18.0 | https://bitbucket.org/xerial/sqlite-jdbc | https://bitbucket.org/xerial/sqlite-jdbc |
| H2 | 1.4.195 | http://www.h2database.com | https://github.com/h2database/h2database |
| C3P0 | 0.9.5.2 | http://www.mchange.com/projects/c3p0/ | https://github.com/swaldman/c3p0 |