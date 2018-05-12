# Event Handling in Spring


### Local Pub-Sub with Application Events and Listeners

Without any special configuration, Spring automatically works as a broker in a local *pub/sub* environment. To publish events, use the `publishEvent` method on an instance of `org.springframework.context.ApplicationEventPublisher` with an instance of `org.springframework.context.ApplicationEvent`. An instance of `ApplicationEventPublisher` can be obtained via DI or by implementing `org.springframework.context.ApplicationEventPublisherAware`.

Subscribing to an event or a hierarchy of events is only a matter of implementing the `ApplicationListener<T extends ApplicationEvent>`. Since it is not possible to implement the same interface multiple times, if you need to listen to multiple events in the same service you can use anonymous inner classes.

```java
// Import statements...

@SpringBootApplication
class EventDemoApplication

fun main(args: Array<String>) {
    runApplication<EventDemoApplication>(*args)
}

class SomeEvent(source: Any) : ApplicationEvent(source)
class AnotherEvent(source: Any) : ApplicationEvent(source)

@Component
class SomePublishingComponent(private val publisher: ApplicationEventPublisher) : CommandLineRunner {

    override fun run(vararg args: String?) {
        while (true) {
            publisher.publishEvent(SomeEvent(this))
            Thread.sleep(2_000)
            publisher.publishEvent(AnotherEvent(this))

        }
    }

    @Bean
    fun someEventListener() = ApplicationListener<SomeEvent> { println("Got some event!!") }
    @Bean
    fun anotherEventListener() = ApplicationListener<AnotherEvent> { println("Got another event!!") }

}

```
