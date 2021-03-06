public class CoffeeHandler extends Handler {

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(request.path().equals("/tea")) {
            response.setStatusCode(HttpStatus.OK.code());
        }
        else {
            response.setStatusCode(HttpStatus.ImATeapot.code());
            response.setBody("I'm a teapot".getBytes());
        }
        return response;
    }

    @Override
    public boolean canHandle(String requestedPath) {
        return requestedPath.equals("/tea") || requestedPath.equals("/coffee");
    }
}
