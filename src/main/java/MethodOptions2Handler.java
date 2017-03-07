public class MethodOptions2Handler extends HandlerBase implements Handler {

    public MethodOptions2Handler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        if (checkMethodAllowed(request, response)) return response;

        response.setStatusCode(HttpStatus.OK.code());
        addOptionsHeader(request, response);

        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/method_options2");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
        allowedMethods.add("OPTIONS");
    }
}
