public class ResponseWriter {
    public String responseString(Response response) {
        String responseToWrite = "HTTP/1.1 " +response.getStatusCode();

       if(response.getBody() != null) {
           responseToWrite += "\r\n\r\n";
           responseToWrite += response.getBody();
       }

       return responseToWrite;
    }
}
