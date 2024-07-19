package Express;
import java.util.ArrayList;
import java.util.List;

public class Response {
    private String body;
    private List<Header> headers;
    private List<Cookie> cookies;
    private int status;

    public Response(String body, List<Header> headers, int status) {
        this.body = body;
        this.headers = headers;
        this.status = status;
    }

    public Response(){
        this.headers = new ArrayList<Header>();
        this.cookies = new ArrayList<>();
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

    public Header getHeader(String headerName) {
        for (Header header : headers)
            if (header.getName().equals(headerName))
                return header;
        return null;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void addHeader(Header header) {
        headers.add(header);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public Cookie getCookie(String name) {
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(name))
                return cookie;
        return null;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "body='" + body + '\'' +
                ", headers=" + headers +
                ", cookies=" + cookies +
                ", status=" + status +
                '}';
    }
}
