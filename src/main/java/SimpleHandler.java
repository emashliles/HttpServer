import java.util.ArrayList;
import java.util.List;

public class SimpleHandler extends HandlerBase implements Handler {

    private final List<String> paths;
    private final Directory publicDirectory;

    public SimpleHandler() {
        super();
        paths = new ArrayList<>();
        publicDirectory = new Directory("public");

        for(int i = 0; i < publicDirectory.getFiles().size(); i++) {
            paths.add("/" + publicDirectory.getFiles().get(i));
        }

        paths.add("/");
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if (checkMethodAllowed(request, response)) return response;

        if (handlePatchRequest(request, response)) return response;

        if (handleLogsRequest(request, response)) return response;

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

    private boolean handleLogsRequest(Request request, Response response) {
        if(request.path().equals("/logs")) {
            if (request.authorization() != null && request.authorization().equals("YWRtaW46aHVudGVyMg==")) {
                response.setStatusCode(HttpStatus.OK.code());
                response.setBody(publicDirectory.getFileContent("logs"));
                return true;
            } else {
                response.setStatusCode(HttpStatus.Unauthorized.code());
                response.addHeader("WWW-Authenticate", "Basic realm=\"logs\"");
                return true;
            }
        }
        return false;
    }

    private boolean handlePatchRequest(Request request, Response response) {
        if(request.httpMethod().equals("PATCH") && request.ifMatch().equals(publicDirectory.getHash("patch-content.txt"))) {
            response.setStatusCode(HttpStatus.NoContent.code());
            publicDirectory.setFileContents("patch-content.txt", request.body());
            return true;
        }
        return false;
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
        allowedMethods.add("PATCH");
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
            body += ("/" + file + "\"");
            body += ">";
            body += file;
            body += "</a></br>";
        }

        body += "</body></html>";
        return body.getBytes();
    }
}
