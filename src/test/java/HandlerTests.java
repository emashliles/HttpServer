import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class HandlerTests {
    @Test
    public void handleSimpleGetRequest() {
        Request request = RequestBuilder.createRequest("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();
        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void returnsTrueIfItCanHandleAPath() {
        Request request = RequestBuilder.createRequest("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertTrue(handler.canHandle(request.path()));
    }

    @Test
    public void returnsFalseIfItCannotHandleAPath() {
        Request request = RequestBuilder.createRequest("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertFalse(handler.canHandle(request.path()));
    }

    @Test
    public void handleMultipleRequests() {
        Request coffeeRequest = RequestBuilder.createRequest("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Request teaRequest = RequestBuilder.createRequest("GET /tea HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new CoffeeHandler();

        assertEquals("200 OK", handler.handleRequest(teaRequest).getStatusCode());
        assertEquals("418 I'm a teapot", handler.handleRequest(coffeeRequest).getStatusCode());
    }

    @Test
    public void handleRedirect() {
        Request request = RequestBuilder.createRequest("GET /redirect HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");

        Handler handler = new RedirectHandler();
        Response response = handler.handleRequest(request);

        assertEquals("302 Redirect", response.getStatusCode());
        assertEquals("http://localhost:5000/", response.getHeaders().get("Location"));
    }

    @Test
    public void handleNotFound() {
        Request request = RequestBuilder.createRequest("GET /foobar HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");

        Handler handler = new NotFoundHandler();

        assertEquals("404 Not Found", handler.handleRequest(request).getStatusCode());
    }

    @Test
    public void includeResponseBody() {
        Request coffeeRequest = RequestBuilder.createRequest("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new CoffeeHandler();

        Response response = handler.handleRequest(coffeeRequest);

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < response.getBody().length; i++) {
            body.append((char) response.getBody()[i]);
        }

        assertEquals("418 I'm a teapot", response.getStatusCode());
        assertEquals("I'm a teapot", body.toString());
    }

    @Test
    public void handleOnlySpecifiedMethods() {
        Request getRequest = RequestBuilder.createRequest("GET /file1 HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Request putRequest = RequestBuilder.createRequest("PUT /file1 HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertEquals("200 OK", handler.handleRequest(getRequest).getStatusCode());
        assertEquals("405 Method Not Allowed", handler.handleRequest(putRequest).getStatusCode());
    }

    @Test
    public void handlePost() {
        Request request = RequestBuilder.createRequest("POST /form HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=hello");
        Handler handler = new FormHandler();

        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void handleOptions() {
        Request request = RequestBuilder.createRequest("OPTIONS /method_options HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new MethodOptionsHandler();

        Response response = handler.handleRequest(request);

        assertEquals("GET,HEAD,POST,OPTIONS,PUT", response.getHeaders().get("Allow"));
    }

    @Test
    public void handlesUnauthenticatedRequest() {
        Request request = RequestBuilder.createRequest("GET /logs HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        Response response = handler.handleRequest(request);

        assertEquals("401 Unauthorized", response.getStatusCode());
        assertEquals("Basic realm=\"logs\"", response.getHeaders().get("WWW-Authenticate"));
    }

    @Test
    public void handlesLogging() {
        Request request = RequestBuilder.createRequest("GET /log HTTP/1.1\r\nHost: localhost:5000\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new LoggingHandler();

        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void handlesAuthenticatedRequest() {
        Request request = RequestBuilder.createRequest("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void handleParametersRequest() {
        Request request = RequestBuilder.createRequest("GET /parameters?parameter1=hello%20! HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new ParametersHandler();

        Response response = handler.handleRequest(request);

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < response.getBody().length; i++) {
            body.append((char) response.getBody()[i]);
        }

        assertEquals("200 OK", response.getStatusCode());
        assertEquals("parameter1 = hello !", body.toString());

    }

    @Test
    public void handleCookies() {
        Request request = RequestBuilder.createRequest("GET /cookie?type=chocolate HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new CookieHandler();

        Response response = handler.handleRequest(request);

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < response.getBody().length; i++) {
            body.append((char) response.getBody()[i]);
        }

        assertEquals("Eat", body.toString());
    }

    @Test
    public void handlePatchUpdates() {
        Request changeRequest = RequestBuilder.createRequest("PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\npatched content");
        Request revertRequest = RequestBuilder.createRequest("PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndefault content");
        Request request = RequestBuilder.createRequest("GET /patch-content.txt HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        Response changeResponse = handler.handleRequest(changeRequest);
        Response revertResponse = handler.handleRequest(revertRequest);
        Response response = handler.handleRequest(request);

        assertEquals("204 No Content", changeResponse.getStatusCode());
        assertEquals("204 No Content", revertResponse.getStatusCode());
        assertEquals("200 OK", response.getStatusCode());

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < response.getBody().length; i++) {
            body.append((char) response.getBody()[i]);
        }

        assertEquals("default content", body.toString());
    }
}

