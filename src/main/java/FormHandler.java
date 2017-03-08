public class FormHandler extends Handler {

    private String data;

    public FormHandler() {
        super();
        data = "";
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(!allowMethod(request.httpMethod())) {
            response.setStatusCode(HttpStatus.MethodNotAllowed.code());
            return response;
        }

        if(request.httpMethod().equals(HttpMethod.POST.toString()) || request.httpMethod().equals(HttpMethod.PUT.toString())) {
            data = request.body();
        }

        if(request.httpMethod().equals(HttpMethod.DELETE.toString())) {
            data = "";
        }

        response.setStatusCode(HttpStatus.OK.code());

        if (!data.equals("")) {
            response.setBody(data.getBytes());
        }

        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/form");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.POST.toString());
        allowedMethods.add(HttpMethod.PUT.toString());
        allowedMethods.add(HttpMethod.GET.toString());
        allowedMethods.add(HttpMethod.DELETE.toString());
    }
}
