import java.util.HashMap;
import java.util.Map;

public class Response {
    private String statusCode;
    private byte[] body;
    private String location;
    private Map<String, String> headers;

    public Response() {
        headers = new HashMap<>();
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
