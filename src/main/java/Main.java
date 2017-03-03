import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        while (true) {
            run();
        }
    }

    public static void run() {
        Router router = new Router();
        router.add(new SimpleHandler());
        router.add(new CoffeeHandler());
        router.add(new RedirectHandler());
        try {
            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket clientSocket = serverSocket.accept();

                 PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())))
            {
                RequestParser parser = new RequestParser();
                Request request = new Request(parser.parseRequest(in));

                Handler handler = router.find(request.path());

                ResponseWriter responseWriter = new ResponseWriter();

                if(handler == null) {
                    out.println("HTTP/1.1 404 Not Found");
                }
                else {
                    Response response = handler.handleRequest(request);
                    out.println(responseWriter.responseString(response));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
