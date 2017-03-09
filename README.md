#Http Server [![Build Status](https://travis-ci.org/emashliles/HttpServer.svg?branch=master)](https://travis-ci.org/emashliles/HttpServer)

HTTP server built to Http specifications and to pass 8th Light Http server task. 

## Run

Clone URL

```
git@github.com:emashliles/HttpServer.git
```

To Build

```
mvn package
```

To run the server

```
```

To run the cob_spec test suite 

```
```

## Adding a new endpoint

* Create a new class that inherits from the abstract class '[Handler](https://github.com/emashliles/HttpServer/blob/master/src/main/java/Handler.java)'.
* Implement abstract methods. 
* To specify endpoint, use the overriden 'canHandle' method to return true for the desired path(s).

```
    @Override
    public boolean canHandle(String path) {
        return path.equals("/parameters");
    }
```

* Specify allowed methods by adding the method to the inherited "allowedMethods" list as a string.

```
    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
    }
```

* Write the code to handle the request in the overridden 'handleRequest' method. You will need to create a new '[Response](https://github.com/emashliles/HttpServer/blob/master/src/main/java/Response.java)' object. 

```
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        
        //Your code here
  
        return response;
    }
```
* Finally, in the '[Main](https://github.com/emashliles/HttpServer/blob/master/src/main/java/Main.java#L15)' class, add the handler to the "[Router](https://github.com/emashliles/HttpServer/blob/master/src/main/java/Router.java)"'s list of handlers.

```
        Router router = new Router();
        router.add(new CoffeeHandler());
        router.add(new RedirectHandler());
        //etc
```
