import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private String method;
    private String path;
    private String body;
    private List<String> parameters;
    private int rangeStart;
    private int rangeEnd;
    private Map<String, String> headerMap;

    private Request(Map<String, String> headers, String body, String method, String path, List parameters) {
        headerMap = headers;
        this.body = body;
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    private static Map<String, String> setSpecificHeaders(List<String> headers) {
        Map<String, String> headerMap = new HashMap<>();
        for (String header : headers) {
            if(!header.contains("HTTP/1.1")) {
                String[] split = header.split(":");
                headerMap.put(split[0], split[1]);
            }
        }
        return headerMap;
    }

    private void parseRanges(String[] rawRanges) {
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

    private String[] getRawRanges(String s) {
        return s.split("=")[1].split("-");
    }

    public static Request createRequest(String rawRequest) {
        String[] requestParts = rawRequest.split("\r\n\r\n");
        String head = requestParts[0];
        String body = null;

        if(requestParts.length == 2) {
            body = requestParts[1];
        }

        List<String> headers = Arrays.asList(head.split("\r\n"));

        String declaration = headers.get(0);
        String[] declarations = declaration.split(" ");
        String method = declarations[0];
        String path = declarations[1];
        List parameters = null;

        if(path.contains("?")) {
            String[] pathAndparams = path.split("\\?");
            path = pathAndparams[0];
            parameters = Arrays.asList(pathAndparams[1].split("&"));
        }

        Request request = new Request(setSpecificHeaders(headers), body, method, path, parameters);
        return request;
    }

    public String httpMethod() {
        return method;
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
        if(headerMap.containsKey("Content-Length")) {
            String rawLength = headerMap.get("Content-Length");
            return Integer.parseInt(rawLength.split(" ")[1]);
        } else return 0;
    }

    public List<String> parameters() {
        return this.parameters;
    }

    public boolean hasRange() {
        return headerMap.containsKey("Range");
    }

    public int rangeStart() {
        String range = headerMap.get("Range");
        String[] rawRanges = getRawRanges(range);
        parseRanges(rawRanges);
        return this.rangeStart;
    }

    public int rangeEnd() {
        return this.rangeEnd;
    }

    public String cookieData() {
        return this.headerMap.get("Cookie");
    }

    public String authorization() {
        if(headerMap.containsKey("Authorization")) {
          return this.headerMap.get("Authorization").split(" ")[2];
        }
        else return null;
    }

    public String ifMatch() {
        if(headerMap.containsKey("If-Match")){
            return headerMap.get("If-Match").split(" ")[1];
        }
        else return null;
    }
}
