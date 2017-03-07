import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static Router router;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        while (true) {
            run();
        }
    }

    public static void run() {
        try {
            Router router = new Router();
            router.add(new CoffeeHandler());
            router.add(new RedirectHandler());
            router.add(new TextFileHandler());
            router.add(new FormHandler());
            router.add(new ParametersHandler());
            router.add(new MethodOptionsHandler());
            router.add(new MethodOptions2Handler());
            router.add(new CookieHandler());
            router.add(new LoggingHandler());
            router.add(new SimpleHandler());
            router.add(new NotFoundHandler());
            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket clientSocket = serverSocket.accept();
                 OutputStream out = clientSocket.getOutputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                 BufferedReader in = new BufferedReader(
                         inputStreamReader))
            {
                RequestParser parser = new RequestParser(in);
                Request request = new Request(parser.parseHeaders());

                if(request.getConentLength() != 0) {
                    request.setBody(parser.parseBody(request.getConentLength()));
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
