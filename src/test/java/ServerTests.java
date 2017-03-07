import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTests {

    @Test
    public void canHandleSimpleGet() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/");

        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        in.close();

        assertEquals("HTTP/1.1 200 OK", headerFields.get(null).get(0));
        assertEquals("text/html", connection.getContentType());
    }

    @Test
    public void canReturnResponseBody() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/");

        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        Map<String, List<String>> headerFields = connection.getHeaderFields();

        String line = "";
        String body = "";
        while ((line = in.readLine()) != null) {
            body += line;
        }

        in.close();

        assertEquals("HTTP/1.1 200 OK", headerFields.get(null).get(0));
        assertTrue(body.contains("<a href=\"/file1\">file1</a>"));
        assertEquals("text/html", connection.getContentType());
    }

    @Test
    public void canReturnPartialFile() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/partial_content.txt");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Range", "bytes=0-7");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        Map<String, List<String>> headerFields = connection.getHeaderFields();

        String line = "";
        String body = "";
        while ((line = in.readLine()) != null) {
            body += line;
        }

        in.close();

        assertEquals("HTTP/1.1 206 Partial Content", headerFields.get(null).get(0));
        assertEquals("This is", body.toString());
        assertEquals("text/plain", connection.getContentType());
    }
    @Test
    public void canReturnFileContents() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/file1");

        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        Map<String, List<String>> headerFields = connection.getHeaderFields();

        String line = "";
        String body = "";
        while ((line = in.readLine()) != null) {
            body += line;
        }

        in.close();

        assertEquals("HTTP/1.1 200 OK", headerFields.get(null).get(0));
        assertTrue(body.contains("file1 contents"));
        assertEquals("text/plain", connection.getContentType());

    }

    @Test
    public void canHandleNonExistantPages() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/foobar");

        URLConnection connection = url.openConnection();

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
        }
        catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);

        }
    }

    @Test
    public void canHandleMethodNotAllowed() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/file1");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("PUT");

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
        }
        catch (IOException e) {
            assertTrue(e.getMessage().equals("Server returned HTTP response code: 405 for URL: http://localhost:5000/file1"));

        }
    }

    @Test
    public void onlyReturnHeadersForHEADRequest() throws IOException {
        Thread server = new Thread(() -> {
            Main.start();
        });

        server.start();

        URL url = new URL("http://localhost:5000/file1");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("HEAD");

        assertEquals(false, connection.getHeaderFields().isEmpty());
        assertEquals(-1, connection.getInputStream().read());

    }
}
