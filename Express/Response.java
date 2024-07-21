package Express;

import java.io.PrintWriter;
import java.util.List;

public class Response extends HttpMessage {
    private boolean sent = false;
    int status = 200;
    PrintWriter out;

    public Response(PrintWriter out){
        this.out = out;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
        addHeader("Content-Length", Integer.toString(body.length()));
    }

    public void send(){
        if(sent)return;
        var msg = new StringBuilder();

        msg.append(httpProtocol).append(" ").append(status).append(System.lineSeparator());
        headers.forEach(msg::append);
        msg.append(System.lineSeparator());
        msg.append(body);

        out.println(msg);
        sent = true;
    }
}
