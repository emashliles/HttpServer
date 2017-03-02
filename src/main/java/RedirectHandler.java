public class RedirectHandler implements Handler {
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode("302 Redirect");
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
