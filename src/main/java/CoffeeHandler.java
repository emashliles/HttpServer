import java.util.ArrayList;
import java.util.List;

public class CoffeeHandler implements Handler {

    private List<String> paths;

    public CoffeeHandler() {
        paths = new ArrayList<>();
        paths.add("/coffee");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode("200 OK");
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
