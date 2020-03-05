[Travis CI Build Server Integration](https://travis-ci.com/ampopp04/fridge-service)      ![Java CI with Gradle](https://github.com/ampopp04/fridge-service/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master) 



# Fridge Service Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/gradle-plugin/reference/html/)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-spring-hateoas)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [JDBC API](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-sql)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)

### Improvements
This is a simple demonstration example that lacks many elements required for a robust production system. A few of these future enhancements are documented as follows.

- Enhance entities to support types and details rather than only a simple name. This basic microservice example utilizes a name as essentially a type for the Fridge and Item entities. You should be able to have Soda items in a Fridge that are specific instances of that type such as Dr. Pepper/Moutain Dew/Coke/Etc.
- Enhance error/exception API Handling
- Tune Logging
- Enhance business layer constraints so that they are adjustable via remote API vs statically defined in properties file
- Production adjusted security rather than using simple authentication for this example.
- Metrics/Performance/Logs/Tracing is supported through Actuator endpoints but could be further enhanced using Spring Boot Admin Client/Server and DropWizard
- Scalability and Parallel Execution Enhancements
  - Make application stateless
  - Containerize using Docker
  - NoSQL/Eureka/Zuul/OpenFeign/Config/Actuator/Monitoring/OAuth
  - Centralized logging/reporting/health-check services 
- Many other enhancements that depend on specific business domain objectives

### OAuth Security GitHub Integration

Security is disabled by default unless running in the prod profile. This is for this example only. GitHub OAuth application integration is used for securing this microservice example. 

* Application name
    * Fridge Service
* Homepage URL
    * http://localhost:8080/
* Authorization callback URL
    * http://localhost:8080/login/oauth2/code/github
* Client ID
    * 1b4f1ffde64d7af52ce2

* API Security Endpoints (Try in Chrome Incognito)
    * http://localhost:8080 will redirect to GitHub OAuth Authorization
    * Watch the security flow by enabling your browsers network debugger
    * Using Network Inspector you can pull off the JSESSIONID Cookie
        * curl -v --cookie "JSESSIONID=A407AB7E1FD8DA16E9BCDEA599138F3C" http://localhost:8080
    * This should be enhanced to not use jsession cookies
 
### Actuator Endpoints
Included is the Spring Boot Actuator module. This supports remote evaluation of application health, metrics, performance, logs, traces, and many other aspects of the system at runtime.


 Navigate to 
```shell script
    http://localhost:8080/actuator/ 
```
if prompted for a password you will see a generated security password in the console if running the system in the default test no-oauth mode.

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "beans": {
      "href": "http://localhost:8080/actuator/beans",
      "templated": false
    },
    "caches-cache": {
      "href": "http://localhost:8080/actuator/caches/{cache}",
      "templated": true
    },
    "caches": {
      "href": "http://localhost:8080/actuator/caches",
      "templated": false
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
    },
    "info": {
      "href": "http://localhost:8080/actuator/info",
      "templated": false
    },
    "conditions": {
      "href": "http://localhost:8080/actuator/conditions",
      "templated": false
    },
    "configprops": {
      "href": "http://localhost:8080/actuator/configprops",
      "templated": false
    },
    "env": {
      "href": "http://localhost:8080/actuator/env",
      "templated": false
    },
    "env-toMatch": {
      "href": "http://localhost:8080/actuator/env/{toMatch}",
      "templated": true
    },
    "loggers": {
      "href": "http://localhost:8080/actuator/loggers",
      "templated": false
    },
    "loggers-name": {
      "href": "http://localhost:8080/actuator/loggers/{name}",
      "templated": true
    },
    "heapdump": {
      "href": "http://localhost:8080/actuator/heapdump",
      "templated": false
    },
    "threaddump": {
      "href": "http://localhost:8080/actuator/threaddump",
      "templated": false
    },
    "metrics": {
      "href": "http://localhost:8080/actuator/metrics",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
      "templated": true
    },
    "scheduledtasks": {
      "href": "http://localhost:8080/actuator/scheduledtasks",
      "templated": false
    },
    "mappings": {
      "href": "http://localhost:8080/actuator/mappings",
      "templated": false
    }
  }
}
```

This is a very simple example of adding monitoring support to the application at a low level. It is suggested to either implement a custom UI solution on top of these endpoints or Spring Boot Admin Client/Server UI. This is best used with Eureka Service Registry/Discovery and a custom standalone Auth/Resource microservice.

### API Resources
The rest API for this example follows the HATEOAS standard. 

Review the Security section above for executing requests with the proper token. This is only required if running in prod profile, otherwise security is disabled for this example.

```shell script
curl http://localhost:8080/fridges -X POST -v --cookie 'JSESSIONID=FBCD1CD9DD8205468B4F25C9756B17D9' -d '{"name":"Fridge1"}' -H 'Content-Type: application/json'
```

#### Fridge Resource

The fridge resource handles fridge entities that define a fridge name and maintains an association of a collection of items contained within the fridge entity.

#### Item Resource

The item resource handles item entities that define an item name and have an association to a fridge entity. Item entities can be created without the need to be immediately associated with a particular fridge entity. 

#### Constraints

Constraints are defined in the application.properties file. There is presently a simple item maximum quantity in fridge constraint defined. This constraint enforces that only a particular maximum of Soda items are allowed in any given fridge at any single time.

### API Example

The root API shows a descriptor for the specification itself that allows simple self documenting navigation.
```shell script
curl http://localhost:8080
{
  "_links" : {
    "items" : {
      "href" : "http://localhost:8080/items{?page,size,sort}",
      "templated" : true
    },
    "fridges" : {
      "href" : "http://localhost:8080/fridges{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/profile"
    }
  }
}
```

The API specifies the profile definition of the API resources and entities.

```shell script
curl http://localhost:8080/profile/items
{
  "alps" : {
    "version" : "1.0",
    "descriptor" : [ {
      "id" : "item-representation",
      "href" : "http://localhost:8080/profile/items",
      "descriptor" : [ {
        "name" : "name",
        "type" : "SEMANTIC"
      }, {
        "name" : "fridge",
        "type" : "SAFE",
        "rt" : "http://localhost:8080/profile/fridges#fridge-representation"
      } ]
    }, {
      "id" : "create-items",
      "name" : "items",
      "type" : "UNSAFE",
      "descriptor" : [ ],
      "rt" : "#item-representation"
    }, {
      "id" : "get-items",
      "name" : "items",
      "type" : "SAFE",
      "descriptor" : [ {
        "name" : "page",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The page to return."
        }
      }, {
        "name" : "size",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The size of the page to return."
        }
      }, {
        "name" : "sort",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The sorting criteria to use to calculate the content of the page."
        }
      } ],
      "rt" : "#item-representation"
    }, {
      "id" : "get-item",
      "name" : "item",
      "type" : "SAFE",
      "descriptor" : [ ],
      "rt" : "#item-representation"
    }, {
      "id" : "delete-item",
      "name" : "item",
      "type" : "IDEMPOTENT",
      "descriptor" : [ ],
      "rt" : "#item-representation"
    }, {
      "id" : "update-item",
      "name" : "item",
      "type" : "IDEMPOTENT",
      "descriptor" : [ ],
      "rt" : "#item-representation"
    }, {
      "id" : "patch-item",
      "name" : "item",
      "type" : "UNSAFE",
      "descriptor" : [ ],
      "rt" : "#item-representation"
    } ]
  }
}
```

The above defines the Item resource profile while the following defines the Fridge resource profile.

```shell script
curl http://localhost:8080/profile/fridges
{
  "alps" : {
    "version" : "1.0",
    "descriptor" : [ {
      "id" : "fridge-representation",
      "href" : "http://localhost:8080/profile/fridges",
      "descriptor" : [ {
        "name" : "name",
        "type" : "SEMANTIC"
      }, {
        "name" : "items",
        "type" : "SAFE",
        "rt" : "http://localhost:8080/profile/items#item-representation"
      } ]
    }, {
      "id" : "create-fridges",
      "name" : "fridges",
      "type" : "UNSAFE",
      "descriptor" : [ ],
      "rt" : "#fridge-representation"
    }, {
      "id" : "get-fridges",
      "name" : "fridges",
      "type" : "SAFE",
      "descriptor" : [ {
        "name" : "page",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The page to return."
        }
      }, {
        "name" : "size",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The size of the page to return."
        }
      }, {
        "name" : "sort",
        "type" : "SEMANTIC",
        "doc" : {
          "format" : "TEXT",
          "value" : "The sorting criteria to use to calculate the content of the page."
        }
      } ],
      "rt" : "#fridge-representation"
    }, {
      "id" : "get-fridge",
      "name" : "fridge",
      "type" : "SAFE",
      "descriptor" : [ ],
      "rt" : "#fridge-representation"
    }, {
      "id" : "delete-fridge",
      "name" : "fridge",
      "type" : "IDEMPOTENT",
      "descriptor" : [ ],
      "rt" : "#fridge-representation"
    }, {
      "id" : "update-fridge",
      "name" : "fridge",
      "type" : "IDEMPOTENT",
      "descriptor" : [ ],
      "rt" : "#fridge-representation"
    }, {
      "id" : "patch-fridge",
      "name" : "fridge",
      "type" : "UNSAFE",
      "descriptor" : [ ],
      "rt" : "#fridge-representation"
    } ]
  }
}
```

The following example demonstrates the creation of fridges and items. The following step shows how to associate an item to a fridge, or in other words, how to put an item, such as a can of Soda, into the fridge. It demonstrates a constraint that limits the amount of Soda cans we aer allowed to put into any single fridge. This example does not demonstrate deleting or updating entities as that HATEOAS compliant exercise is left to the reader.

```shell script
# Query API Root
curl http://localhost:8080 

# Create many fridges
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge1"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge2"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge3"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge4"}' -H 'Content-Type: application/json'

# Create many items, most of which are Soda
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Milk"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Eggs"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Yogurt"}' -H 'Content-Type: application/json'

# Put 13 Soda items into the first fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/8/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/9/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/10/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/17/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/18/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/19/fridge
# The fridge can only hold 12 Soda items. The next curl command attempts to add one too many Soda items to the first fridge. This will violate the Fridge Soda Item Quantity Constraint resulting in an error.
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/20/fridge

# Finally, show a list of all the items in the first fridge. It should be full of Soda and a few other essentials.
curl -i -X GET http://localhost:8080/fridges/1/items

# Other request types such as update and delete exercises are left to the reader to explore.
```

The above example will produce the following output.


```shell script
POPP:~ Andrew$ curl http://localhost:8080

curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge1"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge2"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge3"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge4"}' -H 'Content-Type: application/json'

curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http:{
  "_links" : {
    "fridges" : {
      "href" : "http://localhost:8080/fridges{?page,size,sort}",
      "templated" : true
    },
    "items" : {
      "href" : "http://localhost:8080/items{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/profile"
    }
  }
}POPP:~ Andrew$ 
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge1"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge2"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge3"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge4"}' -H 'Content-Type: application/json'

curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://POPP:~ Andrew$ curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge1"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge2"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge3"}' -H 'Content-Type: application/json'
curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge4"}' -H 'Content-Type: application/json'

curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://local{
  "name" : "Fridge1",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/fridges/1"
    },
    "fridge" : {
      "href" : "http://localhost:8080/fridges/1"
    },
    "items" : {
      "href" : "http://localhost:8080/fridges/1/items"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge2"}' -H 'Content-Type: application/json'
{
  "name" : "Fridge2",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/fridges/2"
    },
    "fridge" : {
      "href" : "http://localhost:8080/fridges/2"
    },
    "items" : {
      "href" : "http://localhost:8080/fridges/2/items"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge3"}' -H 'Content-Type: application/json'
{
  "name" : "Fridge3",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/fridges/3"
    },
    "fridge" : {
      "href" : "http://localhost:8080/fridges/3"
    },
    "items" : {
      "href" : "http://localhost:8080/fridges/3/items"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/fridges -X POST -d '{"name":"Fridge4"}' -H 'Content-Type: application/json'
{
  "name" : "Fridge4",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/fridges/4"
    },
    "fridge" : {
      "href" : "http://localhost:8080/fridges/4"
    },
    "items" : {
      "href" : "http://localhost:8080/fridges/4/items"
    }
  }
}POPP:~ Andrew$ 
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/5"
    },
    "item" : {
      "href" : "http://localhost:8080/items/5"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/5/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/6"
    },
    "item" : {
      "href" : "http://localhost:8080/items/6"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/6/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/7"
    },
    "item" : {
      "href" : "http://localhost:8080/items/7"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/7/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/8"
    },
    "item" : {
      "href" : "http://localhost:8080/items/8"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/8/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/9"
    },
    "item" : {
      "href" : "http://localhost:8080/items/9"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/9/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/10"
    },
    "item" : {
      "href" : "http://localhost:8080/items/10"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/10/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/11"
    },
    "item" : {
      "href" : "http://localhost:8080/items/11"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/11/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/12"
    },
    "item" : {
      "href" : "http://localhost:8080/items/12"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/12/fridge"
    }
  }
}curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Milk"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/13"
    },
    "item" : {
      "href" : "http://localhost:8080/items/13"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/13/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/14"
    },
    "item" : {
      "href" : "http://localhost:8080/items/14"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/14/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Milk"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Eggs"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Yogurt"}' -H 'Content-Type: application/json'


curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/8/fridge
curl {
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/15"
    },
    "item" : {
      "href" : "http://localhost:8080/items/15"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/15/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/16"
    },
    "item" : {
      "href" : "http://localhost:8080/items/16"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/16/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/17"
    },
    "item" : {
      "href" : "http://localhost:8080/items/17"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/17/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/18"
    },
    "item" : {
      "href" : "http://localhost:8080/items/18"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/18/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/19"
    },
    "item" : {
      "href" : "http://localhost:8080/items/19"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/19/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/20"
    },
    "item" : {
      "href" : "http://localhost:8080/items/20"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/20/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Soda"}' -H 'Content-Type: application/json'
{
  "name" : "Soda",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/21"
    },
    "item" : {
      "href" : "http://localhost:8080/items/21"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/21/fridge"
    }
  }
}curl http://localhost:8080/items -X POST -d '{"name":"Milk"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Eggs"}' -H 'Content-Type: application/json'
curl http://localhost:8080/items -X POST -d '{"name":"Yogurt"}' -H 'Content-Type: application/json'


curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/8/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/9/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/10/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/itPOPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Milk"}' -H 'Content-Type: application/json'
{
  "name" : "Milk",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/22"
    },
    "item" : {
      "href" : "http://localhost:8080/items/22"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/22/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Eggs"}' -H 'Content-Type: application/json'
{
  "name" : "Eggs",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/23"
    },
    "item" : {
      "href" : "http://localhost:8080/items/23"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/23/fridge"
    }
  }
}POPP:~ Andrew$ curl http://localhost:8080/items -X POST -d '{"name":"Yogurt"}' -H 'Content-Type: application/json'


curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/8/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/9/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/10/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/u{
  "name" : "Yogurt",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/items/24"
    },
    "item" : {
      "href" : "http://localhost:8080/items/24"
    },
    "fridge" : {
      "href" : "http://localhost:8080/items/24/fridge"
    }
  }
}POPP:~ Andrew$ 
POPP:~ Andrew$ 
POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/8/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/9/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/10/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
curl -i -X PUT -H "Content-Type:text/uri-HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/9/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/10/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/17/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/18/fridge
curl -i -X PUT -H "Content-Type:text/uriHTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/11/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/12/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/17/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/18/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/19/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/20/fridge

curl -i -X GET http://localhost:8080/fHTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/13/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/14/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/15/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/17/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/18/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/19/fridge
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/20/fridge

curl -i -X GET http://localhost:8080/fridges/1/itemsHTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/16/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/17/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/18/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/19/fridge
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Wed, 04 Mar 2020 19:24:22 GMT

POPP:~ Andrew$ curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/fridges/1" http://localhost:8080/items/20/fridge
HTTP/1.1 409 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 04 Mar 2020 19:24:22 GMT

{"cause":null,"message":"Validating item [Item(id=20, name=Soda, fridge=Fridge(id=1, name=Fridge1))] exceeded maximum quantity of [12] allowed in a fridge."}POPP:~ Andrew$ 


POPP:~ Andrew$ curl -i -X GET http://localhost:8080/fridges/1/items
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/hal+json
Transfer-Encoding: chunked
Date: Wed, 04 Mar 2020 19:29:12 GMT

{
  "_embedded" : {
    "items" : [ {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/8"
        },
        "item" : {
          "href" : "http://localhost:8080/items/8"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/8/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/9"
        },
        "item" : {
          "href" : "http://localhost:8080/items/9"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/9/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/10"
        },
        "item" : {
          "href" : "http://localhost:8080/items/10"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/10/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/11"
        },
        "item" : {
          "href" : "http://localhost:8080/items/11"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/11/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/12"
        },
        "item" : {
          "href" : "http://localhost:8080/items/12"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/12/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/13"
        },
        "item" : {
          "href" : "http://localhost:8080/items/13"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/13/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/14"
        },
        "item" : {
          "href" : "http://localhost:8080/items/14"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/14/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/15"
        },
        "item" : {
          "href" : "http://localhost:8080/items/15"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/15/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/16"
        },
        "item" : {
          "href" : "http://localhost:8080/items/16"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/16/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/17"
        },
        "item" : {
          "href" : "http://localhost:8080/items/17"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/17/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/18"
        },
        "item" : {
          "href" : "http://localhost:8080/items/18"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/18/fridge"
        }
      }
    }, {
      "name" : "Soda",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/19"
        },
        "item" : {
          "href" : "http://localhost:8080/items/19"
        },
        "fridge" : {
          "href" : "http://localhost:8080/items/19/fridge"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/fridges/1/items"
    }
  }
}
```
