package Express;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {
    private String method;
    private String url;
    private String body;
    private List<Header> headers = new ArrayList<>();
    private List<Cookie> cookies = new ArrayList<>();

    public Request(String method, String url, String body, List<Header> headers, List<Cookie> cookies) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    public Request(HttpExchange exchange)throws IOException {
        this.method = exchange.getRequestMethod();
        this.url = exchange.getRequestURI().toString();
        this.body = exchange.getRequestBody().toString();

        exchange.getRequestHeaders().entrySet().stream().forEach(header -> {
            if(!header.getKey().equalsIgnoreCase("Cookie"))
                cookies.add(new Cookie(header.getKey(), header.getValue()));
            else
                headers.add(new Header(header.getKey(), header.getValue()));
        });
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                ", cookies=" + cookies +
                '}';
    }
}
