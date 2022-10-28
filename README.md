# Apache Camel DemoSpring 

This demo is intended to showcase the usage of Apache Camel in order to introduce the [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/patterns/messaging/) 
as a theorical basis to message oriented systems.

[Camel EIPs](https://camel.apache.org/components/3.18.x/eips/enterprise-integration-patterns.html)

The includes project samples:

- Sample01: Basic message routing
- Sample02: Splitting and filtering
- Sample03: Kafka integration
- Sample04: Kamelets and Type Conversion
- Sample05: The message aggregation strategy
- Sample06: Error handling

## Running:

```
docker-compose up

./gradlew bootRun
```

For Kafka samples, topis are able for revision in [local Kafka UI](http://localhost:8280/ui/clusters/local/topics) 
 
Posting data to the samples:

- Sample01 single item
`POST http://localhost:8000/sample01`
```
{
    "name": "First sample data"
}
```

- Sample01 list data
  `POST http://localhost:8000/sample01/bulk`
```
{
    "data": [
        {
            "name": "X1"
        },
        {
            "name": "X2"
        }
    ]
}
```

- Sample02

`POST http://localhost:8000/sample02/filter`
```
{
    "data": ["aaa", "bbb", "xxx", "yyy", "zzz"]
}
```

All other samples:
`POST http://localhost:8000/sample{XX}/filter`
where XX is the sample number

```
{
    "data": [
        {
            "name": "X1"
        },
        {
            "name": "X2"
        },
        {
            "name": "X3"
        }
    ]
}
```

# Additional Links

* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#web)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.4/gradle-plugin/reference/html/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Using Apache Camel with Spring Boot](https://camel.apache.org/camel-spring-boot/latest/spring-boot.html)

