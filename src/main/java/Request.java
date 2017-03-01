import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request {

    private List<String> headers;
    private final String method;
    private final String path;
    private final String httpVersion;

    public Request(String rawHeaders) {
        headers = Arrays.asList(rawHeaders.split("\r\n"));
        String declaration = headers.get(0);
        String[] declarations = declaration.split(" ");
        method = declarations[0];
        path = declarations[1];
        httpVersion = declarations[2];
    }

    public int length() {
        return headers.size();
    }

    public String httpMethod() {
        return method;
    }
}
