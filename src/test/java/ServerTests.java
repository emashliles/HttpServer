import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ServerTests {

    private Router router;
    private String PUBLIC_DIR = "src/test/resources/public";

    private String[] args = {"-d", PUBLIC_DIR};
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        router = new Router();
        router.add(new CoffeeHandler());
        router.add(new RedirectHandler());
        router.add(new FormHandler());
        router.add(new ParametersHandler());
        router.add(new MethodOptionsHandler());
        router.add(new MethodOptions2Handler());
        router.add(new CookieHandler());
        router.add(new LoggingHandler(PUBLIC_DIR));
        router.add(new SimpleHandler(PUBLIC_DIR));
        router.add(new NotFoundHandler());
        out = new ByteArrayOutputStream();
    }

    @Test
    public void canHandleSimpleGet() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 200 OK"));
        assertTrue(out.toString().contains("text/html"));
    }

    @Test
    public void canReturnResponseBody() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("<a href=\"/file1\">file1</a>"));
    }
    @Test
    public void canReturnPartialFile() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-7\r\n".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 206 Partial Content"));
        assertTrue(out.toString().contains("This is "));
        assertTrue(out.toString().contains("text/plain"));
    }

    @Test
    public void canReturnFileContents() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("GET /file1 HTTP/1.1\r\n".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 200 OK"));
        assertTrue(out.toString().contains("file1 contents"));
        assertTrue(out.toString().contains("text/plain"));
    }

    @Test
    public void canHandleNonExistantPages() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("GET /foobar HTTP/1.1\r\n".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void canHandleMethodNotAllowed() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("PUT /file1 HTTP/1.1\r\n".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void onlyReturnHeadersForHEADRequest() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("HEAD /file1 HTTP/1.1\r\n".getBytes());

        Server server = new Server(router, in, out);
        server.run();

        assertTrue(out.toString().contains("HTTP/1.1 200 OK"));
        assertFalse(out.toString().contains("\r\n\r\n"));
    }
}
