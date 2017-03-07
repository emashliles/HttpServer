import java.util.ArrayList;
import java.util.List;

public abstract class HandlerBase {

    protected List<String> allowedMethods;

    public HandlerBase() {
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

    protected boolean allowMethod(String method) {
        return allowedMethods.contains(method);
    }
}
