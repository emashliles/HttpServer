public class SimpleHandler implements Handler {
    @Override
    public String path() {
        return "/";
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode("200 OK");
        return response;
    }
}
