import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestBuilder {

    public static Request createRequest(String rawRequest) {
        String[] requestParts = rawRequest.split("\r\n\r\n");
        String head = requestParts[0];
        String body = null;

        if(requestParts.length == 2) {
            body = requestParts[1];
        }

        List<String> headers = Arrays.asList(head.split("\r\n"));

        String declaration = headers.get(0);
        String[] declarations = declaration.split(" ");
        String method = declarations[0];
        String path = declarations[1];

        Map<String, String> parametersMap = null;

        if(path.contains("?")) {
            String[] pathAndparams = path.split("\\?");
            path = pathAndparams[0];
            List parameters = Arrays.asList(pathAndparams[1].split("&"));
            parametersMap =  splitParameters(parameters, "=");
        }

        Request request = new Request(splitParameters(headers, ":"), body, method, path, parametersMap);
        return request;
    }

    private static Map<String, String> splitParameters(List<String> headers, String splitter) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        for (String header : headers) {
            if(!header.contains("HTTP/1.1")) {
                String[] split = header.split(splitter);
                headerMap.put(split[0], split[1]);
            }
        }
        return headerMap;
    }
}
