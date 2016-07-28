package test.example.ekassir.taxi.api.response;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 23.07.2016.
 */
public class OrderPriceResponse extends BaseResponse {

    @SerializedName("amount")
    private String amount;
    @SerializedName("currency")
    private String currency;

    public String getAmount() {
        return amount;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }
}
