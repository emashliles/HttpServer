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

        if(request.httpMethod().equals("POST")) {
            data = request.body();
        }

        if(request.httpMethod().equals("PUT")) {
            data = request.body();
        }

        if(request.httpMethod().equals("DELETE")) {
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
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
        allowedMethods.add("GET");
        allowedMethods.add("DELETE");
    }
}
