import java.util.ArrayList;
import java.util.List;

public class SimpleHandler implements Handler {

    private final List<String> paths;

    public SimpleHandler() {
        paths = new ArrayList<>();
        paths.add("/");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.code());
        response.setBody("Server Content");
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
