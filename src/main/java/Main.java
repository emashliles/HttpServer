import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
            run();
    }

    public static void run() {
        String req;
        try {
            try (ServerSocket serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            ) {

                RequestParser parser = new RequestParser();

                req = parser.parseRequest(in);

                out.println(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
