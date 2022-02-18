package models;

public class Passport {
    private String passportNumber;
    private String passportSeries;

    public void setPassportNumber(String passportNumber1){
        passportNumber = passportNumber1;
    }

    public void setPassportSeries(String passportSeries1){
        passportSeries = passportSeries1;
    }

    public String getPassportNumber(){
        return passportNumber;
    }

    public String getPassportSeries(){
        return passportSeries;
    }
}
