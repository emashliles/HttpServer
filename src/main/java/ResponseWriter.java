import java.util.Map;

public class ResponseWriter {
    public String responseString(Response response) {
        String responseToWrite = "HTTP/1.1 " + response.getStatusCode();

        if(!response.getHeaders().isEmpty()) {
            for (Map.Entry header : response.getHeaders().entrySet()) {
                responseToWrite += "\r\n";
                responseToWrite += (header.getKey() + ": " + header.getValue());
            }
        }

        if(response.getBody() != null) {
            responseToWrite += "\r\n\r\n";
        }
       return responseToWrite;
    }
}
