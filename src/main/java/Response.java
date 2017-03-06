import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private String statusCode;
    private String body;
    private String location;
    private Map<String, String> parameters;

    public Response() {
        parameters = new HashMap<>();
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addHeader(String key, String value) {
        parameters.put(key, value);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
