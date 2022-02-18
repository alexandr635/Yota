package tests;

import io.qameta.allure.Description;
import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import steps.ApiSteps;

public class YotaAbonentActivation {

    private static String token;

    private ApiSteps apiSteps = new ApiSteps();

    @Test
    @Description("Активация абонента с помощью роли ADMIN")
    public void testAdmin() throws Exception {
        token = apiSteps.getToken("admin");
        PhonesResponse phonesResponse = apiSteps.getPhoneResponse(token);
        if (phonesResponse.getPhones().length == 0){
            Assertions.fail("Нет свободных номеров телефонов");
        }

        String id = apiSteps.createCustomer(phonesResponse.getPhones()[0].getPhone(), token);
        apiSteps.checkCorrect(id, token);

        String xml = apiSteps.getXmlBodyForFindCustomerByPhoneNumberModel(token, phonesResponse.getPhones()[0].getPhone());

        apiSteps.changeCustomerStatus(xml, id);
        apiSteps.check200Code(id, token, "ACTIVE");
    }

    @Test
    @Description("Активация абонента с помощью роли USER")
    public void testUser() throws Exception{
        token = apiSteps.getToken("user");
        PhonesResponse phonesResponse = apiSteps.getPhoneResponse(token);
        if (phonesResponse.getPhones().length == 0){
            Assertions.fail("Нет свободных номеров телефонов");
        }

        String id = apiSteps.createCustomer(phonesResponse.getPhones()[0].getPhone(), token);
        apiSteps.checkCorrect(id, token);
        apiSteps.check200Code(id, token, "ACTIVE");
    }
}
