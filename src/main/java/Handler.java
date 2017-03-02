public interface Handler {

    Response handleRequest(Request request);
    boolean canHandle(String path);
}
