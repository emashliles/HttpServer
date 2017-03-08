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
    private String cookieData;
    private String authorization;
    private String ifMatch;

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

                if(rawRanges.length == 2 && !rawRanges[0].equals("")) {
                    rangeStart = Integer.parseInt(rawRanges[0]);
                    rangeEnd = Integer.parseInt(rawRanges[1]);
                }
                else if(rawRanges.length == 1) {
                    rangeStart = Integer.parseInt(rawRanges[0]);
                    rangeEnd = -1;
                }
                else {
                    rangeStart = -1;
                    rangeEnd = Integer.parseInt(rawRanges[1]);
                }
            }

            if(header.contains("Cookie")) {
                this.cookieData = header.split(":")[1];
            }

            if(header.contains("Authorization")) {
                this.authorization = header.split(":")[1].split(" ")[2];
            }

            if(header.contains("If-Match")){
                this.ifMatch = header.split(":")[1].split(" ")[1];
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

    public int getContentLength() {
        return conentLength;
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

    public String cookieData() {
        return this.cookieData;
    }

    public String authorization() {
        return this.authorization;
    }

    public String ifMatch() {
        return this.ifMatch;
    }
}
