public enum HttpStatus {
    OK ("200 OK"),
    Redirect ("302 Redirect"),
    NotFound ("404 Not Found"),
    ImATeapot ("418 I'm a teapot");

    private String status;

    HttpStatus(String status) {
        this.status = status;
    }

    public String code () {
        return status;
    }
}
