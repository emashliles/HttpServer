public class File1Handler implements Handler {
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(!request.httpMethod().equals("GET")) {
            response.setStatusCode(HttpStatus.MethodNotAllowed.code());
        }
        else {
            response.setStatusCode(HttpStatus.OK.code());
        }

        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/file1");
    }
}
