import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
    private BufferedReader in;

    public RequestParser(BufferedReader in) {
        this.in = in;
    }

    public String parseHeaders() {
        StringBuilder rawRequest = new StringBuilder();
        String request;
        String head;

        try {
            while ((head = in.readLine()) != null) {
                if(head.equals("")) {
                    break;
                }
                rawRequest.append(head + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request = rawRequest.toString();
        return request;
    }

    public String parseBody(int contentLength) {
        String body = "";

        for(int i = 0; i < contentLength; i++) {
            try {
                body += (char) in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}
