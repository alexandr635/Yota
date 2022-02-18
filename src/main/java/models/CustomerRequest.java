package models;

public class CustomerRequest {
    private String name;
    private Long phone;
    private AdditionalParameters additionalParameters;

    public CustomerRequest(String name1, Long phone1, String string){
        name = name1;
        phone = phone1;
        additionalParameters = new AdditionalParameters(string);
    }
}
