import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleHandler extends HandlerBase implements Handler {

    private final List<String> paths;
    private final PublicDirectory publicDirectory;

    public SimpleHandler() {
        super();
        paths = new ArrayList<>();
        publicDirectory = new PublicDirectory("public");

        for(int i = 0; i < publicDirectory.getFiles().size(); i++) {
            paths.add("/" + publicDirectory.getFiles().get(i));
        }

        paths.add("/");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if(!allowedMethods.contains(request.httpMethod())) {
            response.setStatusCode(HttpStatus.MethodNotAllowed.code());
            return response;
        }


        String contentType;
        byte[] body;

        if(request.path().equals("/")) {
            contentType = "text/html";
            body = directoryLinks();
        }
        else {
            contentType = publicDirectory.getContentType(request.path());
            body = getBody(request);
        }

        if(request.hasRange()) {
            response.setStatusCode(HttpStatus.PartialContent.code());
        }
        else {
            response.setStatusCode(HttpStatus.OK.code());
        }

        response.addHeader("Content-Type", contentType);
        response.setBody(body);

        return response;
    }

    private byte[] getBody(Request request) {
        byte[] body;

        if(request.hasRange()) {
            body = publicDirectory.getPartialFileContent(request.path(), request.rangeStart(), request.rangeEnd());
        }
        else {
            body = publicDirectory.getFileContent(request.path());
        }
        return body;
    }

    private byte[] directoryLinks() {

        String body = "<html><head></head><body>";

        for (String file : publicDirectory.getFiles()) {
            body += "<a href=\"";
            body += ("http://localhost:5000/" + file + "\"");
            body += ">";
            body += file;
            body += "</a></br>";
        }

        body += "</body></html>";

        return body.getBytes();
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

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
        allowedMethods.add("HEAD");
    }
}
