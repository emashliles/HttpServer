public class ResponseWriter {
    public String responseString(Response response) {
        return "HTTP/1.1 " + response.getStatusCode();
    }
}
