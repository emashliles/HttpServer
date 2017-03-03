public class FormHandler extends HandlerBase implements Handler {

    public FormHandler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(!allowMethod(request.httpMethod())) {
            response.setStatusCode(HttpStatus.MethodNotAllowed.code());
            return response;
        }

        response.setStatusCode(HttpStatus.OK.code());
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/form");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
    }
}
