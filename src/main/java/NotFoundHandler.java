public class NotFoundHandler extends Handler {
    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode(HttpStatus.NotFound.code());
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return true;
    }
}
