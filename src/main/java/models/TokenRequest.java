package models;

public class TokenRequest {
    private String login;
    private String password;

    public TokenRequest(String login1, String pass1){
        login = login1;
        password = pass1;
    }

    public TokenRequest(){}

    public void setLogin(String login1){
        login = login1;
    }

    public void setPassword(String pass){
        password = pass;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }
}
