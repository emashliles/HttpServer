#Http Server [![Build Status](https://travis-ci.org/emashliles/HttpServer.svg?branch=master)](https://travis-ci.org/emashliles/HttpServer)

HTTP server built to Http specifications and to pass 8th Light Http server task. 

## Run

Clone URL

```
git@github.com:emashliles/HttpServer.git
```

To Build

```
```

To run the server

```
```

To run the cob_spec test suite 

```
```

## Adding a new endpoint

* Create a new class that inherits from the abstract class 'Handler'.
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

* Write the code to handle the request in the overridden 'handleRequest' method. You will need to create a new 'Response' object. 

```
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        
        //Your code here
  
        return response;
    }
```
