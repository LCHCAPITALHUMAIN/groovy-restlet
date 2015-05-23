Integrating with Spring using Groovy-Restlet is straightforward.

### Using `ofBean` attribute ###
```
def comp = builder.component(){
    application(uri:""){
        router{
            resource(uri:"/users/{user}", ofBean:"userResource")
        }
    }
}

comp.servers.add(protocol.HTTP, 8182)
```

The bean `userResource` is defined spring definition file as followed:
```
<bean id="userResource" class="org.lpny.gr.example.spring.UserResource"
		scope="prototype" />
```


### Using `ofClass` attribute ###
```
def comp = builder.component(){
    application(uri:""){
        router{
            resource(uri:"/orders", ofClass:"org.lpny.gr.example.spring.OrdersResource")
        }
    }
}

comp.servers.add(protocol.HTTP, 8182)
```
If spring context is specified when creating `GroovyRestlet` instance, Spring'` [AutowireCapableBeanFactory](http://static.springframework.org/spring/docs/2.5.x/api/org/springframework/beans/factory/config/AutowireCapableBeanFactory.html) will be used to create that instance and autowire all necessary properties.