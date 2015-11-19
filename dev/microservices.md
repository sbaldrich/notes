### What's wrong with the classic monolith?

* Developers can't confidently make changes to the application because it is far too intimidating
* Scaling the application can be a challenge (*e.g.* different infrastructure needs for different parts of the application -> the hardware is not easy to find). Scaling a component means scaling *all* components, not only those under heavy load.
* Long-term commitment to a particular technology stack.

### Microservices

![Scale cube](img/scale-cube.jpg "Scale Cube")
> Taken from http://microservices.io/articles/scalecube.html

#### What options are there for *developing* microservices?

* Spring Boot
* Scalatra
* Dropwizard
* NodeJS

#### What options are there for *deploying* microservices?

**+ isolation / manageability - density/efficiency**

* VM / Physical Machines
* Docker
* JVM /Process
* JAR / WAR / OSGI Bundles

**- isolation / manageability + density/efficiency**

#### Microservice partition Strategies

* By noun: *e.g.* **Product info service**
* By verb: *e.g.* **Checkout service**
* By DDD subdomain
* Always do the partition with the Single-reponsibility principle in mind
* Package together things that change at the same time and for the same reasons
* Take UNIX utilities as an example (do one thing right)
* The smallest *atomic* unit of service that delivers business value.

> **Goal:** Most changes affect only one service

#### Drawbacks

* Increased complexity
* Multiple database management
* Testing a distributed system is not trivial
* Deployment strategies must be designed with care
* Development/deployment needs to be carefully coordinated

#### Migrating from the monolith

* Don't do a Big Bang, the only thing you're guaranteed to get is a **Big bang**. (Fowler, I think)
* Apply the Strangler pattern (develop around the older functionalities until they **die**)
* Develop new functionalities as microservices -> **stop digging the hole!**
* Incrementally migrate your old code into services.


## Command Query Responsibility Segregation - (CQRS)

#### Limitations of RDBMS

* Scalability
* Multi data center, distributed database
* Schema updates
* O/R impedance mismatch
* Handling semi-structured data

**We can use microservices and a polyglot architecture but now we have a problem with the distributed data**
*e.g., ACID on SQL databases vs No ACID on NOSQL*

##### How to maintain invariants?

How to maintain the invariants across distributed databases without using a distributed transaction?

**By using an event-driven architecture:**

* Services *publish* events when state changes
* Services *subscribe* to events and update their state
  * Maintain *eventual-consistency*
  * Synchronize databases

#### Problems:

* How to design a domain model based on event sourcing? (**DDD**)
* How to atomically do the db-write and event publishing?
  * ~~2PC~~
  * Transaction log tailing
    * read the database "*transaction log*" and publish the message to the message-broke
    * LinkedIn databus
    * AWS DynamoDB streams
    * MongoDB `oplog`

#### Event Sourcing

Avoid updating the database, just *publish and event*

* For each aggregate (entity)
  * Identify state-changing domain events
  * Define Event Classes
* Persist events and **not** the current state
* Replay events to recreate state (and periodically *snapshot* to avoid loading all events)
**The present is a fold over history**

#### Benefits
* Solves the data consistency issues in Microservices/NoSQL-based architecture
* Reliable event publishing
* Eliminates O/R mapping problem
* Reifies state changes
  * Built-in reliable audit log
  * Temporal queries

#### Drawbacks
* Application rewrite and unfamiliar style of programming
* Detect and handle duplicate events
* Event store only supports PK lookups
  * Must use CQRS to handle queries
  * Application must handle eventually consistent data

#### Queries in an Event-Sourced application

You should use CQRS. Basically you must define the queries you want to support and have a view database that is in charge of providing these queries. It is subscribed to the relevant events and changes according to them.  


### References
* [The Art of Scalability - Abbott, Fischer](http://www.amazon.com/The-Art-Scalability-Architecture-Organizations/dp/0137030428)
* [microservices.io](http://microservices.io)
* [eventuate.io](http://eventuate.io)
* [ebay-base](http://bit.ly/ebaybase)
* [CQRS - Fowler](http://martinfowler.com/bliki/CQRS.html)
