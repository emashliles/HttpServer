import java.net.URLDecoder;
import java.util.*;

public class Request {

    private String method;
    private String path;
    private String body;
    private Map<String, String> parameters;
    private int rangeStart;
    private int rangeEnd;
    private Map<String, String> headerMap;

    public Request(Map<String, String> headers, String body, String method, String path, Map parameters) {
        headerMap = headers;
        this.body = body;
        this.method = method;
        this.path = path;
        this.parameters = parameters;
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

    public Map parameters() {
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

    public String parameter(String parameterKey) {
        return URLDecoder.decode(parameters.get(parameterKey));
    }

    public int parametersCount() {
        return parameters().size();
    }

    public String paramterKey(int index) {
        List<String> values = new ArrayList<>(parameters().keySet());
        return values.get(index).toString();
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

    private String[] getRawRanges(String rangeHeaderValue) {
        return rangeHeaderValue.split("=")[1].split("-");
    }
}
