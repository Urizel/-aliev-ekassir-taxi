package test.example.ekassir.taxi.api.response;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 23.07.2016.
 */
public class OrderAddressResponse extends BaseResponse {

    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;

    @NonNull
    public String getCity() {
        if (city == null){
            city = "";
        }
        return city;
    }

    @NonNull
    public String getAddress() {
        if (address == null){
            address = "";
        }
        return address;
    }
}
