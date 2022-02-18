package steps;

import api.RetrofitApi;
import api.XmlWorker;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import models.*;
import org.junit.jupiter.api.Assertions;
import org.xml.sax.SAXException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;

public class ApiSteps {

    private RetrofitApi retrofitapi;
    private XmlWorker xmlWorker;
    private final String url = "http://localhost:8080/";

    public ApiSteps(){
        retrofitapi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
                .create(RetrofitApi.class);

        xmlWorker = new XmlWorker();
    }

    @Step("Получить xml-тело для кастомера")
    public String getXmlBodyForFindCustomerByPhoneNumberModel(String token, Long phone){
        return "<ns3:Envelope xmlns:ns2=\"soap\" xmlns:ns3=\"http://schemas.xmlsoap.org/soap/envelope\">\n" +
                "   <ns2:Header>\n" +
                "      <authToken>" + token + "</authToken>\n" +
                "   </ns2:Header>\n" +
                "   <ns2:Body>\n" +
                "      <phoneNumber>" + phone + "</phoneNumber>\n" +
                "   </ns2:Body>\n" +
                "</ns3:Envelope>";
    }

    @Step("Проверить сохранение кастомера")
    public void changeCustomerStatus(String xml, String id) throws IOException, ParserConfigurationException, SAXException {
        String id1 = xmlWorker.changeStatus(url, xml);
        Assertions.assertEquals(id1, id, "Не удалось изменить статус");
    }

    @Step("Проверить что статус изменен")
    public void check200Code(String id, String token, String status) throws IOException {
        Assertions.assertEquals(retrofitapi.changeCustomerStatus(token, id, new ChangeCustomerStatusRequest(status)).execute().code(), 200, "Статус не изменен");
    }

    @Step("Проверить корректность активации пользователя")
    public void checkCorrect(String id, String token) throws IOException {
        long start = new Date().getTime();
        while(true){
            Object customerresponce24 = retrofitapi.getCustomerById(token, id).execute().body();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(customerresponce24);
            CustomerResponse2 map = mapper.readValue(json, CustomerResponse2.class);
            Passport passport = mapper.readValue(map.getReturn().getPd(), Passport.class);
            if (map.getReturn() == null)
                Assertions.fail("Некорректное тело ответа");

            if (passport.getPassportSeries().length() != 4 || passport.getPassportNumber().length() != 6)
                Assertions.fail("Некорректные паспортные данные");

            if (start + 120000 <= new Date().getTime()) {
                Assertions.fail("Кастомер не активировался за 2 минуты");
            }

            if (map.getReturn().getStatus().equals("ACTIVE"))
                return;
        }
    }

    @Step("Получить id")
    public String createCustomer(Long phone, String token) throws IOException {
        CustomerResponse response;
        response = retrofitapi.createCustomer(token,
                new CustomerRequest("name", phone, "string")).execute().body();
        if (response == null)
            Assertions.fail("Не удалось создать кастомера");

        return response.getId();
    }

    @Step("Получить токен")
    public String getToken(String who) throws IOException {
        if (who.equals("admin")) {
            return retrofitapi.login(new TokenRequest("admin", "password")).execute().body().getToken();
        }else{
            return retrofitapi.login(new TokenRequest("user", "password")).execute().body().getToken();
        }
    }

    @Step("Получить телефоны")
    public PhonesResponse getPhoneResponse(String token){
        PhonesResponse phonesresponces = null;
        while (true){
            try {
                phonesresponces = retrofitapi.getEmptyPhones(token).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (phonesresponces != null) return phonesresponces;
        }
    }
}
