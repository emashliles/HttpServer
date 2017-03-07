public class LoggingHandler extends HandlerBase implements Handler {
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.code());
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return (path.equals("/log") || path.equals("/these") || path.equals("/requests"));
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
        allowedMethods.add("PUT");
        allowedMethods.add("HEAD");
    }
}
