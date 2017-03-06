import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTests {
    
    @Test
    public void addHeaderToResponse() {
        Response response = new Response();
        response.setStatusCode("200 OK");
        response.addHeader("Allow", "PUT,GET");

        ResponseWriter writer = new ResponseWriter();

        assertEquals("HTTP/1.1 200 OK\r\nAllow: PUT,GET", writer.responseString(response));
    }
}
