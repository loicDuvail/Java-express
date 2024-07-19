package Express;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public class ExpressServer {
    private final HttpServer server;
    private final int port;
    public ExpressServer(int port, int nThreads) throws IOException {
        this.port = port;
        server = HttpServer.create(new InetSocketAddress(port), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        server.setExecutor(threadPoolExecutor);
    }
    public ExpressServer(int port) throws IOException {this(port, 10);}

    public void start() {
        server.start();
        System.out.println("Starting Express server on port " + port);
    }

    private void handle(HttpExchange exchange, Function<Request, Response> cb) throws IOException {
        Request request = new Request(exchange);
        Response response = cb.apply(request);

        int status = response.getStatus() != 0 ? response.getStatus() : 200;
        exchange.sendResponseHeaders(status, response.getBody().length());

        Headers headers = exchange.getResponseHeaders();

        response.getHeaders().forEach(h -> headers.set(h.getName(), h.getValueAsString()));

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBody().getBytes());
        os.close();
    }

    public void get(String path, Function<Request, Response> cb) throws IOException {
        server.createContext(path, httpExchange -> {
            if(httpExchange.getRequestMethod().equals("GET"))
                handle(httpExchange, cb);
        });
    }

    public void post(String path, Function<Request, Response> cb) throws IOException {
        server.createContext(path, httpExchange -> {
            if(httpExchange.getRequestMethod().equals("POST"))
                handle(httpExchange, cb);
        });
    }
}
