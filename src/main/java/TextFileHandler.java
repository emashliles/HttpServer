public class TextFileHandler extends HandlerBase implements Handler {

    public TextFileHandler() {
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
        return path.equals("/text-file.txt");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}
