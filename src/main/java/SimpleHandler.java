import java.io.File;
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

        PublicDirectory publicDirectory = new PublicDirectory("public");
        String body = "";

        for (String file : publicDirectory.getFiles()) {
            body += "<a href=\"";
            body += ("http://localhost:5000/" + file + "\"");
            body += ">";
            body += file;
            body += "</a></br>";
        }

        response.addHeader("Content-Type", "text/html");

        response.setBody(body.getBytes());

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
