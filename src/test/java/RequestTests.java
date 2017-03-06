import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTests {

    private Request request;

    @Before
    public void setUp() {
        request = new Request("GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=myData\r\n");
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

    @Test
    public void separateParameters() {
        request = new Request("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=myData\r\n");
        assertEquals(request.parameters().size(), 2);
    }

    @Test
    public void separateBody() {
        assertEquals(request.body(), "data=myData\r\n");
    }
}
