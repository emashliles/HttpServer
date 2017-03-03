import java.util.ArrayList;
import java.util.List;

public abstract class HandlerBase {

    protected List<String> allowedMethods;

    public HandlerBase() {
        allowedMethods = new ArrayList<>();
        addAllowedMethods();
    }

    protected abstract void addAllowedMethods();

    protected boolean allowMethod(String method) {
        return allowedMethods.contains(method);
    }
}
