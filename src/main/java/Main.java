import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static Router router;

    public static void main(String[] args) {
        router = new Router();
        router.add(new CoffeeHandler());
        router.add(new RedirectHandler());
        router.add(new FormHandler());
        router.add(new ParametersHandler());
        router.add(new MethodOptionsHandler());
        router.add(new MethodOptions2Handler());
        router.add(new CookieHandler());
        router.add(new LoggingHandler());
        router.add(new SimpleHandler());
        router.add(new NotFoundHandler());
        start(router);
    }

    public static void start(Router router) {
        while (true) {
            run(router);
        }
    }

    public static void run(Router router) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
            new Server(router, clientSocket).run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
