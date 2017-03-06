public class CookieHandler extends HandlerBase implements Handler {
    public CookieHandler() {
        super();
    }

    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();

        response.setStatusCode(HttpStatus.OK.code());

        if(request.path().equals("/eat_cookie")) {
            String body = "mmmm" + request.cookieData();
            response.setBody(body.getBytes());
        }
        else {
            response.setBody("Eat".getBytes());

            String cookieData = "";

            for (String parameter :request.parameters()) {
                if(parameter.contains("type")) {
                    cookieData = parameter.split("=")[1];
                }
            }
            response.addHeader("Set-Cookie", cookieData);
        }

        return response;
    }

    @Override
    public boolean canHandle(String path) {
        return (path.equals("/cookie") || path.equals("/eat_cookie"));
    }

    @Override
    protected void addAllowedMethods() {
        allowedMethods.add("GET");
    }
}
