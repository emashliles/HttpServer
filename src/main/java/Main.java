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
        new Server(router).invoke();
    }

}
