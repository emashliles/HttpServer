import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            router.add(new SimpleHandler());
            router.add(new CoffeeHandler());
            router.add(new RedirectHandler());
            router.add(new File1Handler());
            router.add(new TextFileHandler());
            router.add(new FormHandler());
            router.add(new ParametersHandler());
            router.add(new MethodOptionsHandler());
            router.add(new MethodOptions2Handler());
            router.add(new CookieHandler());
            router.add(new NotFoundHandler());
            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket clientSocket = serverSocket.accept();

                 PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
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
                out.println(responseWriter.responseString(response));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
