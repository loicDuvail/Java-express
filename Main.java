import Express.ExpressServer;
import Express.Header;
import Express.Request;
import Express.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        for(String arg: args)
            System.out.println(arg);
        try{
        basicServer();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void basicServer() throws IOException{
        ExpressServer server = new ExpressServer(8080);

        server.get("/", request -> {
            Response response = new Response();
            response.setBody("Hello World");
            response.setStatus(200);
            response.addHeader(new Header("Content-Type", List.of("text/html", "charset=UTF-8")));
            return response;
        });

        server.start();
    }


}
