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
}
