public enum HttpMethod {
    GET ("GET"),
    PUT ("PUT"),
    POST("POST"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
