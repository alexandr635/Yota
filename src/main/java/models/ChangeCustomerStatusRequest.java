package models;

public class ChangeCustomerStatusRequest {

    private String status;

    public ChangeCustomerStatusRequest(String status1){
        status = status1;
    }

    public void setStatus(String status1){
        status = status1;
    }

    public String getStatus(){
        return status;
    }
}
