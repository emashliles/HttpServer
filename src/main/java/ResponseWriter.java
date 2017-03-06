import java.util.Map;

public class ResponseWriter {
    public String responseString(Response response) {
        String responseToWrite = "HTTP/1.1 " + response.getStatusCode();

        if(response.getLocation() != null) {
            responseToWrite += "\r\n";
            responseToWrite += response.getLocation();
        }

        if(!response.getParameters().isEmpty()) {
            for (Map.Entry header : response.getParameters().entrySet()) {
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
