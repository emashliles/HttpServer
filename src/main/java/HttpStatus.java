public enum HttpStatus {
    OK ("200 OK"),
    PartialContent("206 Partial Content"),
    Redirect ("302 Redirect"),
    NotFound ("404 Not Found"),
    ImATeapot ("418 I'm a teapot"),
    Unauthorized("401 Unauthorized"),
    MethodNotAllowed("405 Method Not Allowed");

    private String status;

    HttpStatus(String status) {
        this.status = status;
    }

    public String code () {
        return status;
    }
}
