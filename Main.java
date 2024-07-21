import Express.ExpressServer;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try{
            basicServer();
        }catch(Exception e){System.out.println(e.getMessage());}
    }

    public static void basicServer() throws IOException{
        ExpressServer server = new ExpressServer();

        server.middleware((req, res)->{
            System.out.println("Incoming request:");
            System.out.println(req.getRaw());
        });

        server.get("/", (req, res)->{
            res.setBody("Hello World");
            res.addHeader("Content-Type", new String[]{"text/html", "text/plain"});
            res.send();
        });

        server.post("/", (req, res)->{
            res.addHeader("Content-Type", new String[]{"text/html", "text/plain"});
            res.setBody("Mirror!" + System.lineSeparator() + req.getBody());
            res.send();
        });

        final int PORT = 8080;
        server.listen(PORT, s -> {System.out.println("Socket opened on port " + PORT);});
    }


}
