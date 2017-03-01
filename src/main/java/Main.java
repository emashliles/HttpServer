import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<String> headers;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\r\n");

            headers = new ArrayList<>();
            while (scanner.hasNext()) {
                String header = scanner.nextLine();
                headers.add(header);
                System.out.print(header);
            }

            out.println("HTTP/1.1 200 OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
