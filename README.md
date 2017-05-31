# BasicSql [![Build Status](https://img.shields.io/travis/FerusTech/BasicSql.svg)](https://travis-ci.org/FerusTech/BasicSql)
A utility for easily managing queries and executions to your database.

## Using BasicSql

BasicSql can range from exceedingly simplistic usage...
```java
public static void executeString(final String sql) {
    BasicSql.execute(sql);
}
```

To a much more familiar (though, less repetitive) usage:
```java
private static final MySqlDatabase DATABASE = new MySqlDatabase(
        "localhost", 
        3306, 
        "users", 
        "sql-admin", 
        "secret-password"
);

private static final int DEFAULT_AGE = 18;

public static int getAgeOf(final String user) {
    return BasicSql.returnQuery(DATABASE, "SELECT * FROM user_data WHERE user_id=?", 
            s -> s.setString(1, user), 
            r -> r.next() ? r.getInt("age") : DEFAULT_AGE)
            .orElse(DEFAULT_AGE);
}
```

After creating a database object, you can assign it as the default, for future usage.
```java
public static void executeString(final String sql) {
    if (!Database.isDefaultDatabaseSet()) {
        Database.setDefaultDatabase(DATABASE);
    }

    BasicSql.execute(sql);
}
```

## Download
Latest Version: [![Maven Central](https://img.shields.io/maven-central/v/tech.ferus.util/BasicSql.svg)]()

Replace **VERSION** with version shown in button above.

**Maven**
```xml
<dependency>
    <groupId>tech.ferus.util</groupId>
    <artifactId>BasicSql</artifactId>
    <version>VERSION</version>
</dependency>
```

**Gradle**
```groovy
repositories {
    mavenCentral()
}

dependencies {
    compile 'tech.ferus.util:BasicSql:VERSION'
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
| C3P0 | 0.9.5.2 | http://www.mchange.com/projects/c3p0/ | https://github.com/swaldman/c3p0 |