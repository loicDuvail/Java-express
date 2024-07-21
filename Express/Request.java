package Express;

import Express.exceptions.HeaderNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Request extends HttpMessage {
    private String path;

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

    public String getPath() {
        return path;
    }
}
