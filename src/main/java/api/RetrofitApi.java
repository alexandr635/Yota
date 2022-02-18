package api;

import models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitApi {

    @POST("login")
    @Headers("Content-Type: application/json")
    Call<TokenResponse> login(@Body TokenRequest body);

    @GET("simcards/getEmptyPhone")
    @Headers("Content-Type: application/json")
    Call<PhonesResponse> getEmptyPhones(@Header("authToken") String token);

    @POST("customer/postCustomer")
    @Headers("Content-Type: application/json")
    Call<CustomerResponse> createCustomer(@Header("authToken") String token, @Body CustomerRequest request);

    @GET("customer/getCustomerById")
    Call<Object> getCustomerById(@Header("authToken") String token, @Query("customerId") String customer);

    @POST("customer/{customerId}/changeCustomerStatus")
    @Headers("Content-Type: application/json")
    Call<Void> changeCustomerStatus(@Header("authToken") String token, @Path("customerId") String status,
                                    @Body ChangeCustomerStatusRequest changecustomerstatusrequest);
}
