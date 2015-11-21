## Notes about Spring Framework
1. [Application Events and Listeners](#events)

<a name="events"></a>
#### Application Events and Listeners

Without any special configuration, Spring automatically works as a broker in a local *pub/sub* environment. To publish events, use the `publishEvent` method on an instance of `org.springframework.context.ApplicationEventPublisher` with an instance of `org.springframework.context.ApplicationEvent`. An instance of `ApplicationEventPublisher` can be obtained via DI or by implementing `org.springframework.context.ApplicationEventPublisherAware`.

Subscribing to an event is only a matter of implementing the `ApplicationListener<T extends ApplicationEvent>`. It is also possible to subscribe to a hierarchy of events. Since in java it is not possible to implement the same interface multiple times disregarding the type parameters, anonymous inner classes can be used:

```java
@Service
public class FooService{
  public void doSomething(){}

  @Bean
  public ApplicationListener<BarEvent> fooService$barEventListener(){
    return new ApplicationListener<BarEvent>(){
      @Override
      public void onApplicationEvent(BarEvent event){
        FooService.this.doSomething();
      }
    }
  }

  …

}
```
