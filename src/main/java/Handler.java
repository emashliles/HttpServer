import java.util.ArrayList;
import java.util.List;

public abstract class Handler {

    protected List<String> allowedMethods;

    public Handler() {
        allowedMethods = new ArrayList<>();
        addAllowedMethods();
    }

    protected void addOptionsHeader(Request request, Response response) {
        if(request.httpMethod().equals("OPTIONS")) {
            StringBuilder options = new StringBuilder();

            for (String method : allowedMethods) {
                options.append(method + ",");
            }
            options.deleteCharAt(options.length() - 1);

            response.addHeader("Allow", options.toString());
        }
    }

    protected boolean checkMethodAllowed(Request request, Response response) {
        if(!allowedMethods.contains(request.httpMethod())) {
            response.setStatusCode(HttpStatus.MethodNotAllowed.code());
            return true;
        }
        return false;
    }

    protected abstract void addAllowedMethods();
    public abstract Response handleRequest(Request request);
    public abstract boolean canHandle(String path);

    protected boolean allowMethod(String method) {
        return allowedMethods.contains(method);
    }
}
