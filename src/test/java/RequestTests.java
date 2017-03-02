import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTests {

    private Request request;

    @Before
    public void setUp() {
        request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n");
    }

    @Test
    public void canSeparateHeaders() {
        assertEquals(5, request.length());
    }

    @Test
    public void separateMethod() {
        assertEquals(request.httpMethod(), "GET");
    }

    @Test
    public void separateHTTPVersion() {
        assertEquals(request.httpVersion(), "HTTP/1.1");
    }

    @Test
    public void separatePath() {
        assertEquals(request.path(), "/");
    }
}
