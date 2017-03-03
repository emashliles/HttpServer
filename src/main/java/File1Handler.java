public class File1Handler extends HandlerBase implements Handler {

    public File1Handler() {
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
        return path.equals("/file1");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}