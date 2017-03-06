import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestParserTests {
    private String exampleRequest = "GET / HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n";
    private String separator = "\r\n";
    private String exampleBody ="data=myData";

    @Test
    public void getRequestFromInput() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(exampleRequest.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        RequestParser parser = new RequestParser(reader);

        assertEquals(exampleRequest, parser.parseHeaders());
    }

    @Test
    public void parseBody() {
        ByteArrayInputStream in = new ByteArrayInputStream((exampleRequest + separator +exampleBody).getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        RequestParser parser = new RequestParser(reader);
        parser.parseHeaders();

        assertEquals(exampleBody, parser.parseBody(11));
    }
}
