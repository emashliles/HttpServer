import java.io.BufferedReader;

public class RequestParser {
    public String parseRequest(BufferedReader in) {
        String request = "";
        String line;

        try {
            while ((line = in.readLine()) != null) {
                if(line.equals("")) {
                    break;
                }
                request += (line + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
