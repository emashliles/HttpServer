import java.net.URLDecoder;

public class ParametersHandler extends Handler {

    public ParametersHandler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if (checkMethodAllowed(request, response)) return response;

        String responseBody = "";

        for(String parameter : request.parameters()) {
            String[] param = parameter.split("=");
            responseBody += param[0] + " = ";
            responseBody += URLDecoder.decode(param[1]);
        }

        response.setStatusCode(HttpStatus.OK.code());
        response.setBody(responseBody.getBytes());
        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("parameters");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}
