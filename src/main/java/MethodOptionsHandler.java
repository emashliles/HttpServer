public class MethodOptionsHandler extends Handler {

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if (checkMethodAllowed(request, response)) {
            return response;
        }

        response.setStatusCode(HttpStatus.OK.code());
        addOptionsHeader(request, response);

        return response;
    }

    @Override
    public boolean canHandle(String path) {
       return path.equals("/method_options");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
        allowedMethods.add(HttpMethod.HEAD.toString());
        allowedMethods.add(HttpMethod.POST.toString());
        allowedMethods.add(HttpMethod.OPTIONS.toString());
        allowedMethods.add(HttpMethod.PUT.toString());
    }
}
