import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class HandlerTests {
    @Test
    public void handleSimpleGetRequest() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();
        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void returnsTrueIfItCanHandleAPath() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertTrue(handler.canHandle(request.path()));
    }

    @Test
    public void returnsFalseIfItCannotHandleAPath() {
        Request request = new Request("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertFalse(handler.canHandle(request.path()));
    }

    @Test
    public void handleMultipleRequests() {
        Request coffeeRequest = new Request("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Request teaRequest = new Request("GET /tea HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new CoffeeHandler();

        assertEquals("200 OK", handler.handleRequest(teaRequest).getStatusCode());
        assertEquals("418 I'm a teapot", handler.handleRequest(coffeeRequest).getStatusCode());
    }

    @Test
    public void handleRedirect() {
        Request request = new Request("GET /redirect HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");

        Handler handler = new RedirectHandler();
        Response response = handler.handleRequest(request);

        assertEquals("302 Redirect", response.getStatusCode());
        assertEquals("Location: http://localhost:5000/", response.getLocation());
    }

    @Test
    public void handleNotFound() {
        Request request = new Request("GET /foobar HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");

        Handler handler = new NotFoundHandler();

        assertEquals("404 Not Found", handler.handleRequest(request).getStatusCode());
    }

    @Test
    public void includeResponseBody() {
        Request coffeeRequest = new Request("GET /coffee HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
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
        Request getRequest = new Request("GET /file1 HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Request putRequest = new Request("PUT /file1 HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new SimpleHandler();

        assertEquals("200 OK", handler.handleRequest(getRequest).getStatusCode());
        assertEquals("405 Method Not Allowed", handler.handleRequest(putRequest).getStatusCode());
    }

    @Test
    public void handlePost() {
        Request request = new Request("POST /form HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=hello");
        Handler handler = new FormHandler();

        Response response = handler.handleRequest(request);

        assertEquals("200 OK", response.getStatusCode());
    }

    @Test
    public void handleOptions() {
        Request request = new Request("OPTIONS /method_options HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new MethodOptionsHandler();

        Response response = handler.handleRequest(request);

        assertEquals("GET,HEAD,POST,OPTIONS,PUT", response.getParameters().get("Allow"));
    }

    @Test
    public void handleCookies() {
        Request request = new Request("GET /cookie?type=chocolate HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
        Handler handler = new CookieHandler();

        Response response = handler.handleRequest(request);

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < response.getBody().length; i++) {
            body.append((char) response.getBody()[i]);
        }

        assertEquals("Eat", body.toString());
    }
}
