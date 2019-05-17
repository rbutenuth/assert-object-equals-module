# Assert Object Equals Anypoint Module

## Mule supported versions
Mule 4.x (tested on 4.1.4 version)
In case you are working with Mule 3.x, have a look [here](https://github.com/rbutenuth/assert-object-equals-connector), there you find a version suitable for Mule 3.

## Installation

To install this module via Maven, please add following repository and dependency to yur pom.xml file

```xml
<repository>
    <id>central</id>
    <name>artifactory.codecentric.de-releases</name>
    <url>https://artifactory.codecentric.de/artifactory/public</url>
</repository>

<dependency>
    <groupId>de.codecentric.mule.modules</groupId>
    <artifactId>assert-object-equals-module</artifactId>
    <version>1.1.0</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## Usage

The connector can be used for Object structures or XML:

* [Object structures and JSON](../docs/compare-objects.md)
* [XML](../docs/compare-xml.md)

## Mule example app

Mule 4 example application can be checked out here:
* https://gitlab.codecentric.de/danijel.dragicevic/assert-object-equal-module-mule-app-example

## CI

Pipelines for this project:
* https://gitlab.codecentric.de/danijel.dragicevic/assert-object-equals-module/pipelines?scope=branches&page=1

## Reporting Issues

We use GitLab Issues for tracking issues with this connector. You can report new issues at this link:
* https://gitlab.codecentric.de/danijel.dragicevic/assert-object-equals-module/issues.