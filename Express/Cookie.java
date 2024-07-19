package Express;

import java.util.List;

public class Cookie{
    private String name;
    private List<String> value;
    public Cookie(String name, List<String> value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}