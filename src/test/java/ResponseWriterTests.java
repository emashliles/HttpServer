import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseWriterTests {
    @Test
    public void createResponseStringOfHeaders() {
        Response response = new Response();
        response.setStatusCode("200 OK");

        ResponseWriter writer = new ResponseWriter();
        String responseString = writer.responseString(response);

        assertEquals("HTTP/1.1 200 OK", responseString);
    }
}
