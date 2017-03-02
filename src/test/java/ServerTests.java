import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

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
    }
}
