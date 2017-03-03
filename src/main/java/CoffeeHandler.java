import java.util.ArrayList;
import java.util.List;

public class CoffeeHandler implements Handler {

    private List<String> paths;

    public CoffeeHandler() {
        paths = new ArrayList<>();
        paths.add("/coffee");
        paths.add("/tea");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(request.path().equals("/tea")) {
            response.setStatusCode("200 OK");
        }
        else {
            response.setStatusCode("418 I'm a teapot");
            response.setBody("I'm a teapot");
        }
        return response;
    }

    @Override
    public boolean canHandle(String requestedPath) {
        for(String path : paths) {
            if(path.equals(requestedPath)) {
                return true;
            }
        }
        return false;
    }
}
