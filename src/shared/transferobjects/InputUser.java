package shared.transferobjects;

import java.io.Serializable;

public class InputUser implements Serializable {


    private String output;


    public InputUser(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
