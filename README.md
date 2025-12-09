# Assert Object Equals Anypoint Module


## Maven Dependency

Add this dependency to your application pom.xml (check for newer version):

```xml
<dependency>
    <groupId>de.codecentric.mule.modules</groupId>
    <artifactId>assert-object-equals-module</artifactId>
    <version>1.2.0</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

The module is available on [Maven Central](https://mvnrepository.com/), so you don't need it to compile/install it yourself.

## Usage

The connector can be used for Object structures or XML:

* [Object structures and JSON](https://github.com/rbutenuth/assert-object-equals-module/blob/main/docs/compare-objects.md)
* [XML](https://github.com/rbutenuth/assert-object-equals-module/blob/main/docs/compare-xml.md)

## Release notes

### 1.2.0 2024-07-19

- Switched to Java SDK 1.6.3
- Support to run in Mule runtime with Java 17

### 1.1.2 2019-11-01

- Ported from Mule 3 connector
