public class RedirectHandler extends Handler {

    public static final String SERVER_URL = "http://localhost:5000/";

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode(HttpStatus.Redirect.code());
        response.setBody(SERVER_URL.getBytes());
        response.addHeader("Location", SERVER_URL);
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/redirect");
    }
}
