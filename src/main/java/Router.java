import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Handler> handlers;

    public Router() {
        handlers = new ArrayList<>();
    }

    public Handler find(String path) {
        for(Handler handler : handlers) {
            if(handler.canHandle(path)) {
                return handler;
            }
        }
        return null;
    }

    public void add(Handler handler) {
        handlers.add(handler);
    }
}
