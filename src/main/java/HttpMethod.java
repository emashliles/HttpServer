public enum HttpMethod {
    GET ("GET"),
    PUT ("PUT"),
    POST("POST"),
    DELETE("OPTIONS");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
