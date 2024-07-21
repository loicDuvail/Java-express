package Express;

import Express.exceptions.HeaderNotFoundException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ExpressServer {
    private final List<Interceptor> interceptors = new ArrayList<>();
    private final List<BiConsumer<Request, Response>> middlewares = new ArrayList<>();
    private BiConsumer<Request, Response> notFoundHandler = (req, res) -> {
        res.setStatus(404);
        res.addHeader("Content-Type", "text/plain");
        res.setBody("Not Found");
        res.send();
    };
    private BiConsumer<Request, Response> internalErrorHandler = (req, res) -> {
        res.setStatus(500);
        res.addHeader("Content-Type", "text/plain");
        res.setBody("Internal Error");
        res.send();
    };
    private BiConsumer<Request, Response> invalidRequestHandler = (req, res) -> {
        res.setStatus(400);
        res.addHeader("Content-Type", "text/plain");
        res.setBody("Invalid Request");
        res.send();
    };


    public ExpressServer(){
    }

    private void handleExchange(BufferedReader in, PrintWriter out) throws IOException {
        var response = new Response(out);
        Request request;
        try{
            request = new Request(in);
        }catch(Exception e){
            invalidRequestHandler.accept(null, response);
            return;
        }

        try{
            middlewares.forEach(m -> m.accept(request, response));

            for(Interceptor interceptor : interceptors)
                if(interceptor.shouldIntercept(request)){
                    interceptor.intercept(request, response);
                    return;
                }
        }catch(Exception e){
            internalErrorHandler.accept(request, response);
        }

        notFoundHandler.accept(request, response);

        out.flush();
        out.close();
    }

    /**
     * @param flexPath path supporting '*', '{param}' notation
     */
    private boolean pathMatch(String path, String flexPath){
        return path.equals(flexPath);
    }

    public void listen(int port, Consumer<ServerSocket> cb) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            cb.accept(serverSocket);

            System.out.println("__________________________________________________");
            System.out.println();

            while(true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected: " + clientSocket.getInetAddress());
                System.out.println();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                handleExchange(in, out);

                clientSocket.close();

                System.out.println("__________________________________________________");
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void listen(int port) throws IOException {
        listen(port, s->{});
    }

    public void route(String method, String path, BiConsumer<Request, Response> cb){
        var interceptor = new Interceptor();
        interceptor.setShouldIntercept((request)->method.equalsIgnoreCase(request.getMethod()) && pathMatch(request.getPath(), path));
        interceptor.setIntercept(cb);
        interceptors.addFirst(interceptor);
    }

    public void get(String path, BiConsumer<Request, Response> cb){
        route("GET", path, cb);
    }

    public void post(String path, BiConsumer<Request, Response> cb){
        route("POST", path, cb);
    }

    public void put(String path, BiConsumer<Request, Response> cb){
        route("PUT", path, cb);
    }

    public void delete(String path, BiConsumer<Request, Response> cb){
        route("DELETE", path, cb);
    }

    public void middleware(BiConsumer<Request, Response> middleware){
        middlewares.addFirst(middleware);
    }

    public void onNotFound(BiConsumer<Request, Response> notFoundHandler){
        this.notFoundHandler = notFoundHandler;
    }

    public void onInternalError(BiConsumer<Request, Response> internalErrorHandler){
        this.internalErrorHandler = internalErrorHandler;
    }
}
