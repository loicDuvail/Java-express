package Express;

import java.util.Arrays;

public class Header {
    private String name;
    private String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Header(String name, String[] values) {
        this.name = name;
        setValue(values);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(String[] values){
        this.value = Arrays.stream(values).reduce("", (acc, v)->(acc.isEmpty() ? acc : acc + "; ") + v);
    }

    @Override
    public String toString() {
        return (name + ": " + value + System.lineSeparator());
    }
}
