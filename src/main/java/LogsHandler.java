public class LogsHandler extends HandlerBase implements Handler {
    public LogsHandler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        response.setStatusCode(HttpStatus.Unauthorized.code());
        response.addHeader("WWW-Authenticate", "Basic realm=\"logs\"");
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/logs");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");

    }
}
