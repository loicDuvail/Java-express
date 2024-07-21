package Express;

import Express.exceptions.HeaderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public abstract class HttpMessage {
    protected String raw;
    protected String httpProtocol = "HTTP/1.1";
    protected String method;
    protected List<Header> headers = new ArrayList<>();
    protected String body;

    public HttpMessage(String raw,String httpProtocol, String method, List<Header> headers, String body) {
        this.raw = raw;
        this.httpProtocol = httpProtocol;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public HttpMessage(){}

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getHttpProtocol() {return httpProtocol;}

    public void setHttpProtocol(String httpProtocol) {this.httpProtocol = httpProtocol;}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public Header getHeader(String name) throws HeaderNotFoundException {
        for (Header header : headers)
            if (header.getName().equalsIgnoreCase(name))
                return header;
        throw new HeaderNotFoundException(name);
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        this.headers.add(new Header(name, value));
    }

    public void addHeader(String name, String[] values){
        if(headers.stream().anyMatch(h -> h.getName().equals(name)))
            return;
        this.headers.add(new Header(name, values));
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
