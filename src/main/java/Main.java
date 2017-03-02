import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        while (true) {
            run();
        }
    }

    public static void run() {
        Router router = new Router();
        router.add(new SimpleHandler());
        router.add(new CoffeeHandler());
        try {
            try (ServerSocket serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))) {
                RequestParser parser = new RequestParser();
                Request request = new Request(parser.parseRequest(in));

                Handler handler = router.find(request.path());
                if(handler == null) {
                    out.println("404");
                }
                else {
                    Response response = handler.handleRequest(request);
                    out.println(response.getStatusCode());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
