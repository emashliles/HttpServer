public class CookieHandler extends HandlerBase implements Handler {
    public CookieHandler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        response.setStatusCode(HttpStatus.OK.code());

        response.setBody("Eat");
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/cookie");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}
