import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTests {

    @Test
    public void canSeparateHeaders() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate");
        assertEquals(5, request.length());
    }

    @Test
    public void separateMethod() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate");
        assertEquals(request.httpMethod(), "GET");
    }

    @Test
    public void separateHTTPVersion() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate");
        assertEquals(request.httpVersion(), "HTTP/1.1");
    }

    @Test
    public void separatePath() {
        Request request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate");
        assertEquals(request.path(), "/");
    }
}
