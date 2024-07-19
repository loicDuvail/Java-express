package Express;

import java.util.ArrayList;
import java.util.List;

public class Header{
    private String name;
    private List<String> value;
    public Header(String name, List<String> value){
        this.name = name;
        this.value = value;
    }
    public Header(String name, String value){
        this.name = name;
        this.value = new ArrayList<>();
        this.value.add(value);
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

    public String getValueAsString(){
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < value.size(); i++){
            str.append(value.get(i));
            if(i != value.size()-1)
                str.append("; ");
        }

        return str.toString();
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public void setValue(String value){
        this.value.add(value);
    }
}