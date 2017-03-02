public interface Handler {
    String path();

    Response handleRequest(Request request);
}
