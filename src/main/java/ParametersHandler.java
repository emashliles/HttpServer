import java.net.URLDecoder;
import java.util.Map;

public class ParametersHandler extends Handler {

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if (checkMethodAllowed(request, response)) return response;

        String responseBody = parametersToString(request);

        response.setStatusCode(HttpStatus.OK.code());
        response.setBody(responseBody.getBytes());
        return response;
    }

    private String parametersToString(Request request) {
        String responseBody = "";

        for(int i = 0; i < request.parametersCount(); i ++) {
            String parameterKey = request.paramterKey(i);
            responseBody += parameterKey + " = " +  request.parameter(parameterKey);
        }
        return responseBody;
    }

    @Override
    public boolean canHandle(String path) {
        return path.equals("/parameters");
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}
