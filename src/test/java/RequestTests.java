import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RequestTests {

    private Request request;

    @Before
    public void setUp() {
        request = RequestBuilder.createRequest("GET / HTTP/1.1\r\nHost: localhost:5000\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nCookie: data\r\nRange: bytes=0-4\r\nIf-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=myData\r\n");
    }

    @Test
    public void separateMethod() {
        assertEquals(request.httpMethod(), "GET");
    }

    @Test
    public void separatePath() {
        assertEquals(request.path(), "/");
    }

    @Test
    public void separateParameters() {
        request = RequestBuilder.createRequest("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\ndata=myData\r\n");
        assertEquals(request.parametersCount(), 2);
        assertEquals("variable_2", request.paramterKey(1));
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", request.parameter("variable_1"));
    }

    @Test
    public void separateRequestRange() {
        assertTrue(request.hasRange());
        assertEquals(0, request.rangeStart());
        assertEquals(4, request.rangeEnd());
    }

    @Test
    public void separateBody() {
        assertEquals(request.body(), "data=myData\r\n");
    }

    @Test
    public void separateCookieHeader() {
        assertEquals(" data", request.cookieData());
    }

    @Test
    public void separateAuthHeader() {
        assertEquals("YWRtaW46aHVudGVyMg==", request.authorization());
    }

    @Test
    public void separateEtag() {
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", request.ifMatch());
    }
}
