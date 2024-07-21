package Express;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class Interceptor {
    private Function<Request, Boolean> shouldIntercept;
    private BiConsumer<Request, Response> intercept;

    public Interceptor(){}
    public Interceptor(Function<Request, Boolean> shouldIntercept, BiConsumer<Request, Response> intercept) {
        this.shouldIntercept = shouldIntercept;
        this.intercept = intercept;
    }

    public boolean shouldIntercept(Request request) {
        return shouldIntercept.apply(request);
    }

    public void intercept(Request request, Response response) {
        intercept.accept(request, response);
    }

    public void setShouldIntercept(Function<Request, Boolean> shouldIntercept) {
        this.shouldIntercept = shouldIntercept;
    }

    public void setIntercept(BiConsumer<Request, Response> intercept) {
        this.intercept = intercept;
    }
}
