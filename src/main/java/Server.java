import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Server {
    private Router router;
    private Socket clientSocket;

    public Server(Router router, Socket clientSocket) {
        this.router = router;
        this.clientSocket = clientSocket;
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
                Request request = new Request(parser.parseHeaders());

                if(request.getContentLength() != 0) {
                    request.setBody(parser.parseBody(request.getContentLength()));
                }

                Handler handler = router.find(request.path());

                ResponseWriter responseWriter = new ResponseWriter();

                Response response = handler.handleRequest(request);
                out.write(responseWriter.responseString(response).getBytes());

                if (response.getBody() != null || !request.httpMethod().equals("HEAD")) {
                    out.write(response.getBody());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
