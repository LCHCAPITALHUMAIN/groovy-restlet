**Groovy Restlet** is a simple [Groovy](http://groovy.codehaus.org) DSL for constructing [Restlet](http://www.restlet.org) application.

**GroovyRestlet is not hosted by Groovy as a Groovy Module, please check http://docs.codehaus.org/display/GROOVY/GroovyRestlet for more information**
### Requirement ###
Current build 0.1.0-SNAPSHOT based on following technologies:
  * Restlet 1.1-SNAPSHOT
  * Slf4J 1.4.3
  * CGLib 2.1\_3
  * Spring 2.5
  * commons-lang 2.3

### Simple Usage ###
  1. Create an instance of `GroovyRestlet` first.
```
GroovyRestlet gr = new GroovyRestlet()
```
    * If you want [Spring](http://www.springframework.org) support, provide an instance of [ApplicationContext](http://static.springframework.org/spring/docs/2.5.x/api/org/springframework/context/ApplicationContext.html) as the constructor parameter.
```
GroovyRestlet gr = new GroovyRestlet(appCtx);
```
  1. Preparing your building script using simple GroovyRestlet DSL syntax, for example see
> SpringIntegrationExamples
  1. Calling `GroovyRestlet.build(URI)` then done.
```
gr.build(uriToScript)
```
