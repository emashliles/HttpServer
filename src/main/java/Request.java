import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Request {

    private List<String> headers;
    private final String method;
    private final String path;
    private final String httpVersion;
    private String body;
    private int conentLength;

    public Request(String rawRequest) {

        String[] request = rawRequest.split("\r\n\r\n");
        String head = request[0];

        if(request.length == 2) {
            body = request[1];
        }

        headers = Arrays.asList(head.split("\r\n"));

        for (String header : headers) {
            if(header.contains("Content-Length")) {
                String rawLength = header.split(":")[1];
                conentLength = Integer.parseInt(rawLength.split(" ")[1]);
            }
        }

        String declaration = headers.get(0);
        String[] declarations = declaration.split(" ");
        method = declarations[0];
        path = declarations[1];
        httpVersion = declarations[2];
    }

    public int length() {
        return headers.size();
    }

    public String httpMethod() {
        return method;
    }

    public String httpVersion() {
        return httpVersion;
    }

    public String path() {
        return path;
    }

    public String body() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getConentLength() {
        return conentLength;
    }

    public void setConentLength(int conentLength) {
        this.conentLength = conentLength;
    }
}
