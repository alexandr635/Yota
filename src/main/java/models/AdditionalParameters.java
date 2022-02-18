package models;

public class AdditionalParameters {
    private String string;

    public AdditionalParameters(String string1){
        string = string1;
    }

    public AdditionalParameters(){
    }

    public void setString(String string1){
        string = string1;
    }

    public String getString(){
        return string;
    }
}
