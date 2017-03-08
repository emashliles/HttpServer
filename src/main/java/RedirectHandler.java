public class RedirectHandler extends Handler {
    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode(HttpStatus.Redirect.code());
        response.setBody("http://localhost:5000/".getBytes());
        response.setLocation("Location: http://localhost:5000/");
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        if(path.equals("/redirect")) {
            return true;
        }
        return false;
    }
}
