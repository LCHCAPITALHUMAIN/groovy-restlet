## Everything starts from `GroovyRestlet` ##
In order to using this Groovy DSL to construct Restlet application, you must first create an instance of `GroovyRestlet`.
```
GroovyRestlet gr = new GroovyRestlet(); 
```
Or if you would like Spring integration, you can feed an instance of `ApplicationContext` to constructor of `GroovyRestlet`.
```
GroovyRestlet gr = new GroovyRestlet(appCtx);
```
Once a `GroovyRestlet` instance is created, you can use it to build `Restlet` components by feeding it a `URI` of your script file.
```
gr.build(uriToScript);
```
The return value of `build` method is depended on the return value of your script.
> According to Groovy Spec, the result of last statement of a Groovy script will be returned as the return value of an execution of the script.

## Global variables ##

For convenience, Groovy-Restlet adds following global variables to Groovy Context:
  1. `builder` - the entry point of construction. You can directly refer this variable in your script
  1. `protocol` - shortcut to [Protocol](http://www.restlet.org/documentation/1.1/api/org/restlet/data/Protocol.html); you can refer an instance of predefined [Protocol](http://www.restlet.org/documentation/1.1/api/org/restlet/data/Protocol.html) by using `protocol.NAME` i.e. `protocol.HTTP`;
  1. `mediaType` - shortcut to [MediaType](http://www.restlet.org/documentation/1.1/api/org/restlet/data/MediaType.html);
  1. `status` - shortcut to [Status](http://www.restlet.org/documentation/1.1/api/org/restlet/data/Status.html)
  1. `challengeScheme` - shortcut to [ChallengeScheme](http://www.restlet.org/documentation/1.1/api/org/restlet/data/ChallengeScheme.html)
  1. `redirectorMode` - shortcut to mode of [Redirector](http://www.restlet.org/documentation/1.1/api/org/restlet/Redirector.html)
  1. `routingMode` - shortcut to mode of [Router](http://www.restlet.org/documentation/1.1/api/org/restlet/Router.html)

Above variables can be directly referred in scripts.

## Constructors ##

### `restlet` ###
### `component` ###
### `application` ###
### `router` ###
### `directory` ###
### `redirector` ###
### `client` ###
### `server` ###
### `filter` ###
### `guard` ###
### `resource` ###