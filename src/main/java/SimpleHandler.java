import java.util.ArrayList;
import java.util.List;

public class SimpleHandler extends Handler {

    private final List<String> paths;
    private final Directory publicDirectory;

    public SimpleHandler(String publicDir) {
        super();
        paths = new ArrayList<>();
        publicDirectory = new Directory(publicDir);
        directoryFiles();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        if (checkMethodAllowed(request, response)) return response;
        if (handlePatchRequest(request, response)) return response;
        if (handleLogsRequest(request, response)) return response;

        response.addHeader("Content-Type", contentType(request.path()));
        response.setBody(getBody(request));
        response.setStatusCode(getHttpStatus(request).code());

        return response;
    }

    @Override
    public boolean canHandle(String requestedPath) {
        for (String path : paths) {
            if (path.equals(requestedPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add(HttpMethod.GET.toString());
        allowedMethods.add(HttpMethod.HEAD.toString());
        allowedMethods.add(HttpMethod.PATCH.toString());
    }

    private HttpStatus getHttpStatus(Request request) {
        HttpStatus status;
        if (request.hasRange()) {
            status = HttpStatus.PartialContent;
        } else {
            status = HttpStatus.OK;
        }
        return status;
    }

    private byte[] getBody(Request request) {
        byte[] body;
        if (request.path().equals("/")) {
            body = directoryLinks();
        } else {
            body = getFileBody(request);
        }
        return body;
    }

    private String contentType(String path) {
        String contentType;
        if (path.equals("/")) {
            contentType = "text/html";
        } else {
            contentType = publicDirectory.getContentType(path);
        }
        return contentType;
    }

    private boolean handlePatchRequest(Request request, Response response) {
        if (request.httpMethod().equals(HttpMethod.PATCH.toString()) && request.ifMatch().equals(publicDirectory.getHash("patch-content.txt"))) {
            response.setStatusCode(HttpStatus.NoContent.code());
            publicDirectory.setFileContents("patch-content.txt", request.body());
            return true;
        }
        return false;
    }

    private boolean handleLogsRequest(Request request, Response response) {
        if (request.path().equals("/logs")) {
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

    private byte[] getFileBody(Request request) {
        byte[] body;

        if (request.hasRange()) {
            body = publicDirectory.getPartialFileContent(request.path(), request.rangeStart(), request.rangeEnd());
        } else {
            body = publicDirectory.getFileContent(request.path());
        }
        return body;
    }

    private byte[] directoryLinks() {
        String body = "<html><head></head><body>";

        for (String file : publicDirectory.getFiles()) {
            body += "<a href=\"/" + file + "\">" + file + "</a></br>";
        }

        body += "</body></html>";
        return body.getBytes();
    }

    private void directoryFiles() {
        for (int i = 0; i < publicDirectory.getFiles().size(); i++) {
            paths.add("/" + publicDirectory.getFiles().get(i));
        }
        paths.add("/");
    }
}
