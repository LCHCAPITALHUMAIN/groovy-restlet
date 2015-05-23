For example groovy scripts, see http://code.google.com/p/groovy-restlet/source/browse/trunk/groovy-restlet/src/test/groovy/org/lpny/gr/examples/tutorials/

### [Part02](http://www.restlet.org/documentation/1.1/tutorial#part02) ###
Example about creating a [Restlet Client](http://www.restlet.org/documentation/1.1/api/org/restlet/Client.html) instance.

You can use **shortcut** `client` constructor as shown in `#1`; also you can use generic `restlet` constructor to create client instance.

> Tip: you can use `restlet(ofClass:[class name|class instance])` to create any instance of derived type of [Restlet](http://www.restlet.org/documentation/1.1/api/org/restlet/Restlet.html). In this case, you need to specify attribute `consArgs` which are an array of constructing parameters.

```
//#1 using shortcut
builder.client(protocol.HTTP)

//#2 generic restlet constructor
builder.restlet(ofClass:"org.restlet.Client", consArgs:[protocol.HTTP] as Object[])
```

### [Part03](http://www.restlet.org/documentation/1.1/tutorial#part03) ###
_At current moment, server construction does not support `restlet` way_

Nesting here indicates a parent-child relationship.

> Tip: the child of a `server` component becoming the [target](http://www.restlet.org/documentation/1.1/api/org/restlet/Server.html#setTarget(org.restlet.Restlet)) property of the [Server](http://www.restlet.org/documentation/1.1/api/org/restlet/Server.html).
```
builder.server(protocol:protocol.HTTP,
        port:8182){
    restlet(handle:{req, resp->
        resp.setEntity("Hello World", mediaType.TEXT_PLAIN)
    })
}.start()

//or you can define restlet first
def restlet = builder.restlet(handle:{req, resp->
    resp.setEntity("Hello World", mediaType.TEXT_PLAIN)
})
builder.server(protocol:protocol.HTTP,
        port:8182, target:restlet).start()
```

### [Part05](http://www.restlet.org/documentation/1.1/tutorial#part05) ###
```
//using component shortcut constructor
def component = builder.component{
    current.servers.add(protocol.HTTP, 8182)
    
    restlet(uri:"/trace", handle: {req, resp->
        println "To process request: ${req}"
        def message = """Resource URI: ${req.resourceRef}
Root URI : ${req.rootRef}
Routed part : ${req.resourceRef.baseRef}
Remaining part: ${req.resourceRef.remainingPart}"""
        resp.setEntity(message, mediaType.TEXT_PLAIN)
    })
}
```

### [Part06](http://www.restlet.org/documentation/1.1/tutorial#part06) ###
```

def ROOT_URI = ""
builder.component{
    current.servers.add(protocol.HTTP, 8182)
    current.clients.add(protocol.FILE)
    
    application(uri:""){
        directory(root:ROOT_URI)
    }
}.start()
```

### [Part09](http://www.restlet.org/documentation/1.1/tutorial#part09) ###
> Tip: using `current` to get the current instance.

By default any nested component will be automatically attached to its parent component according to their parent-child relationship. Here [guard](http://www.restlet.org/documentation/1.1/api/org/restlet/Guard.html) is automatically attached to its parent ([Application](http://www.restlet.org/documentation/1.1/api/org/restlet/Application.html) here) as its [root](http://www.restlet.org/documentation/1.1/api/org/restlet/Application.html#setRoot(org.restlet.Restlet)). Adding attribute `autoAttach:false` can disable this feature.
```
builder.component{
    current.servers.add(protocol.HTTP, 8182)
    application(uri:""){
        guard(scheme:challengeScheme.HTTP_BASIC, realm:"Tutorial")
            .secrets.put("scott","tiger".toCharArray())
            
        def dir = directory(autoAttach:false, root:"")
        current.root.next=dir
    }
}.start()
```

### [Part10](http://www.restlet.org/documentation/1.1/tutorial#part10) ###
In Restlet, an attaching operation (on Router.attach) returns an instance of [Route](http://www.restlet.org/documentation/1.1/api/org/restlet/Route.html). It might be needed to do some post processing on a route. Attribute `postAttach` which refers a closure is used to support this.
```
builder.component{
    current.servers.add(protocol.HTTP, 8182)
    application(uri:""){
        def router = router{
            def target = "http://www.google.com/search?q={keywords}"
            redirector(uri:"/search",
                    targetTemplate:target, mode:redirectorMode.MODE_CLIENT_TEMPORARY,
                    postAttach:{route->
                        route.extractQuery("keywords","kwd",true)
            })
        }        
    }
}.start()
```

### [Part11](http://www.restlet.org/documentation/1.1/tutorial#part11) ###
> Tip: You can implement the [handle method](http://www.restlet.org/documentation/1.1/api/org/restlet/Restlet.html#handle(org.restlet.data.Request,%20org.restlet.data.Response)) of a [Restlet](http://www.restlet.org/documentation/1.1/api/org/restlet/Restlet.html) using a groovy closure
```
builder.component{
    current.servers.add(protocol.HTTP, 8182)
    
    application(uri:""){
        router{
            def guard = guard(uri:"/docs", scheme:challengeScheme.HTTP_BASIC, 
                    realm:"Restlet Tutorials")
            guard.secrets.put("scott", "tiger".toCharArray())
            guard.next = directory(root:"", autoAttach:false)
            
            restlet(uri:"/users/{user}", handle:{req,resp->
                resp.setEntity("Account of user \"${req.attributes.get('user')}\"",
                        mediaType.TEXT_PLAIN)
            })
            
            restlet(uri:"/users/{user}/orders", handle:{req, resp->
                resp.setEntity("Orders or user \"${req.attributes.get('user')}\"",
                        mediaType.TEXT_PLAIN)
            })
            
            restlet(uri:"/users/{user}/orders/{order}", handle:{req, resp->
                def attrs = req.attributes
                def message = "Order \"${attrs.get('order')}\" for User \"${attrs.get('user')}\""
                resp.setEntity(message, mediaType.TEXT_PLAIN)
            })
        }
    }
}.start()
```

### [Part12](http://www.restlet.org/documentation/1.1/tutorial#part12) ###
Same as handle closure of a Restlet, you can implement a simple [Restlet Resource](http://www.restlet.org/documentation/1.1/api/org/restlet/resource/Resource.html) using groovy closures. Following attributes are supported:
  * init:  `init` method
  * represent: for `represent()` and `represent(Variant)` methods **HTTP GET**
  * store: for `storeRepresentation()` method **HTTP PUT**
  * remove: for `remoteRepresentation()` method **HTTP DELETE**
  * accept: for `acceptRepresentation()` method **HTTP POST**
  * head: for `handleHead()` method
  * options: for `handleOptions` method

> Tip: Specify `self` parameter in the last of parameter list. This special `self` instance indicates the resource instance.

```
builder.component{
    current.servers.add(protocol.HTTP, 8182)
    
    application(uri:""){
        router{
            resource("/users/{user}",
                    init:{ctx, req, resp, self->                           
                        self.getVariants().add(new Variant(mediaType.TEXT_PLAIN))
                    },
                    represent:{variant, self->                        
                        return new StringRepresentation(
                                "Account of user \"${self.request.attributes.get('user')}".toString(),
                                mediaType.TEXT_PLAIN);
                    })
             resource("/users/{user}/orders", ofClass:OrdersResource)
        }
    }
}.start()
```