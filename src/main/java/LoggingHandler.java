public class LoggingHandler extends Handler {

    private final Directory publiDirectory;

    public LoggingHandler(String publicDir) {
        super();
        publiDirectory = new Directory(publicDir);
    }

    @Override
    public Response handleRequest(Request request) {
        publiDirectory.setFileContents("logs", request.httpMethod() + " " + request.path() + " HTTP/1.1\r\n", true);

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
        allowedMethods.add(HttpMethod.GET.toString());
        allowedMethods.add(HttpMethod.PUT.toString());
        allowedMethods.add(HttpMethod.HEAD.toString());
    }
}
