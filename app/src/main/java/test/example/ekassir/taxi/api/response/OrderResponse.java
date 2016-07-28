package test.example.ekassir.taxi.api.response;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import android.util.Log;
import com.google.gson.annotations.SerializedName;

import java.text.*;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 23.07.2016.
 */
public class OrderResponse extends BaseResponse {

    private static final String TAG = "OrderResponse";

    @SerializedName("id")
    private long id;
    @SerializedName("startAddress")
    private OrderAddressResponse startAddress;
    @SerializedName("endAddress")
    private OrderAddressResponse endAddress;
    @SerializedName("price")
    private OrderPriceResponse price;
    @SerializedName("orderTime")
    private String orderTime;
    @SerializedName("vehicle")
    private OrderVehicleResponse vehicle;

    private String resultPrice;
    private String date;
    private String orderDate;
    private long millis;

    public long getId() {
        return id;
    }

    @NonNull
    public OrderAddressResponse getStartAddress() {
        if (startAddress == null){
            startAddress = new OrderAddressResponse();
        }
        return startAddress;
    }

    @NonNull
    public OrderAddressResponse getEndAddress() {
        if (endAddress == null){
            endAddress = new OrderAddressResponse();
        }
        return endAddress;
    }

    @NonNull
    public OrderPriceResponse getPrice() {
        if (price == null){
            price = new OrderPriceResponse();
        }
        return price;
    }

    @NonNull
    public String getOrderTime()  {
        if (orderTime == null){
            orderTime = "";
        }
        return orderTime;
    }

    @NonNull
    public OrderVehicleResponse getVehicle() {
        if (vehicle == null){
            vehicle = new OrderVehicleResponse();
        }
        return vehicle;
    }

    @NonNull
    public String getFormattedOrderDate(){
        Date parseDate;
        if (orderDate == null) {
            if (!orderTime.isEmpty()) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
                            Locale.getDefault());
                    parseDate = dateFormat.parse(orderTime);
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    orderDate = dateFormat.format(parseDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return orderDate;
    }

    @NonNull
    public String getFormattedDate(){
        if (date == null){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            date = dateFormat.format(new Date(getMillis()));
        }
        return date;
    }

    @NonNull
    public String getFormattedPrice() {
        if (resultPrice == null){
            resultPrice = getPrice().getAmount() + " " + getPrice().getCurrency();
        }
        return resultPrice;
    }

    public long getMillis() {
        if (millis == 0) {
            millis = (long)(new Date().getTime() + 1000 * 60 * 60 * 24 * (15 * Math.random()));
        }
        return millis;
    }
}
