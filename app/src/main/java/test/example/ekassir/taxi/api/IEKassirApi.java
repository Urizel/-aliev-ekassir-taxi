package test.example.ekassir.taxi.api;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import test.example.ekassir.taxi.api.response.OrderResponse;

/**
 * Created on 12.06.2016.
 */
public interface IEKassirApi {

    String BASE_DOMAIN = "http://careers.ekassir.com/";
    String IMAGES_PATH = BASE_DOMAIN + "/test/images/";

    @GET("test/orders.json")
    Call<List<OrderResponse>> getOrders();
}
