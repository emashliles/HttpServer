import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Server implements Runnable{
    private Router router;
    private Socket clientSocket;
    private ResponseWriter responseWriter;

    public Server(Router router, Socket clientSocket) {
        this.router = router;
        this.clientSocket = clientSocket;
        this.responseWriter = new ResponseWriter();
    }

    public void run() {
        try {
            try (
                 OutputStream out = clientSocket.getOutputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                 BufferedReader in = new BufferedReader(
                         inputStreamReader))
            {
                RequestParser parser = new RequestParser(in);
                Request request = RequestBuilder.createRequest(parser.parseHeaders());

                if(request.getContentLength() != 0) {
                    request.setBody(parser.parseBody(request.getContentLength()));
                }

                Handler handler = router.find(request.path());

                Response response = handler.handleRequest(request);
                out.write(responseWriter.responseString(response).getBytes());

                if (response.getBody() != null || !request.httpMethod().equals(HttpMethod.HEAD.toString())) {
                    out.write(response.getBody());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
