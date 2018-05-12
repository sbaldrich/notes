# Notes about Spring Framework
1. [Internationalization](#i18n)
1. [Application Events and Listeners](#events)

<a name="i18n"></a>
## Internationalization

Configure a `MessageSource` and a `LocaleResolver`. Optionally (and recommended) you can define a `LocaleChangeInterceptor` to allow changing the locale using a query parameter.

```java

@Bean
 public MessageSource messageSource() {
   ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
   messageSource.setCacheSeconds(-1);
   messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
   messageSource.setBasenames("/WEB-INF/i18n/messages", "/WEB-INF/i18n/validation");
   return messageSource;
 }

@Override
  public void addInterceptors(InterceptorRegistry registry) {
    super.addInterceptors(registry);
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    registry.addInterceptor(localeChangeInterceptor);
  }

@Bean
  public LocaleResolver localeResolver() {
    return new SessionLocaleResolver();
  }
```
