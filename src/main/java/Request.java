import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Request {

    private List<String> headers;
    private final String method;
    private String path;
    private final String httpVersion;
    private String body;
    private int conentLength;
    private List<String> parameters;
    private boolean hasRange;
    private int rangeStart;
    private int rangeEnd;

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

            if(header.contains("Range: bytes=")) {
                hasRange = true;
                String[] rawRanges = header.split(":")[1].split("=")[1].split("-");
                rangeStart = Integer.parseInt(rawRanges[0]);
                rangeEnd = Integer.parseInt(rawRanges[1]);
            }
        }

        String declaration = headers.get(0);
        String[] declarations = declaration.split(" ");
        method = declarations[0];
        extractPathAndParamters(declarations);
        httpVersion = declarations[2];
    }

    private void extractPathAndParamters(String[] declarations) {
        String path = declarations[1];

        if(path.contains("?")) {
            String[] pathAndparams = path.split("\\?");
            this.path = pathAndparams[0];
            parameters = Arrays.asList(pathAndparams[1].split("&"));
        }
        else {
            this.path = path;
        }

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

    public List<String> parameters() {
        return this.parameters;
    }

    public boolean hasRange() {
        return this.hasRange;
    }

    public int rangeStart() {
        return this.rangeStart;
    }

    public int rangeEnd() {
        return this.rangeEnd;
    }
}
