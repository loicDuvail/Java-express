package Express;

import Express.exceptions.HeaderNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Request extends HttpMessage {
    private String path;
    public Request(String raw, String httpProtocol, String method, List<Header> headers, String body, String path) {
        super(raw, httpProtocol, method, headers, body);
        this.path = path;
    }
//    public Request(BufferedReader in) throws IOException, HeaderNotFoundException {
//        try {
//            String[] args = in.readLine().split(" ");
//            setMethod(args[0]);
//            setPath(args[1]);
//
//            String line;
//            while (!(line = in.readLine()).isEmpty()) {
//                String[] parts = line.split(": ");
//                addHeader(parts[0], parts[1]);
//            }
//
//            if(Stream.of("GET", "HEAD").anyMatch(m -> m.equalsIgnoreCase(method)))return;
//
//            long contentLength = Long.parseLong(getHeader("Content-Length").getValue());
//            long processedLength = 0;
//            StringBuilder body = new StringBuilder();
//            int read;
//
//            while (processedLength < contentLength && (read = in.read()) != -1) {
//                body.append((char) read);
//                processedLength++;
//            }
//
//            setBody(body.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (HeaderNotFoundException e) {
//            throw new HeaderNotFoundException("Content-Length");
//        }
//    }
    public Request(BufferedReader in) throws IOException, HeaderNotFoundException {
        String[] top = in.readLine().split(" ");
        this.method = top[0];
        this.path = top[1];
        this.httpProtocol = top[2];

        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ");
            this.headers.add(new Header(header[0], header[1]));
        }

        this.raw = method + " " + path + " " + httpProtocol + System.lineSeparator() + headers.stream().reduce("", (acc, h) -> acc + h, String::concat);

        if(Stream.of("GET", "HEAD").anyMatch(s -> s.equalsIgnoreCase(method)))return;

        long contentLength = Long.parseLong(getHeader("Content-Length").getValue());
        long processedLength = 0L;
        StringBuilder body = new StringBuilder();
        int read;

        while(processedLength < contentLength && (read = in.read()) != -1 ){
            body.append((char) read);
            processedLength++;
        }

        this.body = body.toString();

        this.raw += System.lineSeparator() + body;
    }
    public Request(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
