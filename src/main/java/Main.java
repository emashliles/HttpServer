import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static ExecutorService executorService;
    private static String PUBLIC_DIR;

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args) {
        Directory publicDirectory = new Directory(PUBLIC_DIR);
        publicDirectory.createNewEmptyFile("logs");

        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("-d")) {
                PUBLIC_DIR = args[i + 1];
            }
        }

        Router router = new Router();
        router.add(new CoffeeHandler());
        router.add(new RedirectHandler());
        router.add(new FormHandler());
        router.add(new ParametersHandler());
        router.add(new MethodOptionsHandler());
        router.add(new MethodOptions2Handler());
        router.add(new CookieHandler());
        router.add(new LoggingHandler(PUBLIC_DIR));
        router.add(new SimpleHandler(PUBLIC_DIR));
        executorService = Executors.newFixedThreadPool(100);
        run(router);
    }

    public static void run(Router router) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(5000)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.execute(new Server(router, clientSocket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
