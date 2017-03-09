import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Server implements Runnable{
    private Router router;
    private OutputStream out;
    private ResponseWriter responseWriter;
    private InputStream inputStream;

    public Server(Router router, InputStream in, OutputStream out) {
        this.router = router;
        this.out = out;
        this.responseWriter = new ResponseWriter();
        this.inputStream = in;
    }

    public void run() {
        try {
            try (
                 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream)))
            {
                RequestParser parser = new RequestParser(in);
                Request request = RequestBuilder.createRequest(parser.parseHeaders());

                if(request.getContentLength() != 0) {
                    request.setBody(parser.parseBody(request.getContentLength()));
                }

                Handler handler = router.find(request.path());

                Response response = handler.handleRequest(request);
                out.write(responseWriter.responseString(response).getBytes());

                if (response.getBody() != null || !request.httpMethod().equals(HttpMethod.HEAD.toString())) {
                    out.write(response.getBody());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
