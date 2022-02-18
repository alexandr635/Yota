package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResponse2 {

    private Return aReturn;

    public Return getReturn(){
        return aReturn;
    }
    @JsonProperty("return")
    public void setReturn(Return aReturn1){
        aReturn = aReturn1;
    }

    public class Return{
        String customerId;
        String name;
        String status;
        Long phone;
        AdditionalParameters additionalParameters;
        String pd;

        public String getStatus(){
            return status;
        }

        public void setCustomerId(String customerId1){
            customerId = customerId1;
        }

        public void setName(String name1){
            name = name1;
        }

        public void setStatus(String status1){
            status = status1;
        }

        public void setPhone(Long phone1){
            phone = phone;
        }

        @JsonProperty("additionalParameters")
        public void setAdditionalParameters(AdditionalParameters additionalParameters1){
            additionalParameters = additionalParameters1;
        }

        public void setPd(String pd1){
            pd = pd1;
        }

        public String getPd(){
            return pd;
        }
    }
}
