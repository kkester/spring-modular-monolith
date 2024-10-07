# Monomart

## Overview

This PoC demonstrates the following architecture, design, and coding strategies:

1. Hexagonal Architecture leveraging Spring and Modular Monolith architecture pattern.
2. Flyway Database Migrations.
3. Observability utilizing Grafana, Tempo, Loki, and Prometheus.
4. CQS and Event Sourcing Patterns.

### Spring Modular Monolith Summary
* The project consists of two domains; commerce and inventory where each is encapsulated within its own Policy module. 
* Commerce contains Catalog and Product business models while the Inventory contains Cart and Purchases business models.
* The root src directory acts as the Deployable module that brings in all adapter and primary modules as well as the react frontend.
* Product detail pages and Cart Summary page all driven by RESTful APIs.
* The checkout mechanism initiates an API call to the backend where an application event is published from the commerce module and consumed by the inventory module.

This project follows a hexagonal multi-modular architecture pattern where the application is composed of various submodules.  Each module is either a Policy, Primary Adapter, Secondary Adapter, or Deployable module.  Policy modules encapsulate the core domain model and have no dependencies on any other module.  Primary adapter modules depend only on Policy modules and invoke an operation defined by a Policy module.  Secondary Adapter modules also only depend on Policy modules and implement interfaces from a Policy module.

| Module | Module Type                                                      |
| ------ |------------------------------------------------------------------|
| inventory-policy | Policy                                                           |
| inventory-api-adapter | Primary Adapter                                                  |
| inventory-db-adapter | Secondary Adapter for inventory and Primary Adapter for Commerce |
| inventory-event-adapter | Secondary Adapter                                                |
| commerce-policy | Policy                                                           |
| commerce-api-adapter | Primary Adapter                                                  |
| commerce-db-adapter | Secondary Adapter                                                |
| commerce-event-adapter | Primary Adapter                                                  |

### See Also 
* [Monomart Workshop](https://github.com/gballer77/monomart)
* [Spring Modulith Demo](https://github.com/kkester/spring-modulith-demo)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin/packaging-oci-image.html)
* [Distributed Tracing Reference Guide](https://docs.micrometer.io/tracing/reference/index.html)
* [Getting Started with Distributed Tracing](https://docs.spring.io/spring-boot/3.3.4/reference/actuator/tracing.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Prometheus](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#actuator.metrics.export.prometheus)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.flyway)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)