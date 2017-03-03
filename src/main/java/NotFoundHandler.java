public class NotFoundHandler implements Handler {
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode("404 Not Found");
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return true;
    }
}
