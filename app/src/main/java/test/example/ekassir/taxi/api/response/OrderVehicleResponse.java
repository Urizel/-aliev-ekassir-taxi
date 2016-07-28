package test.example.ekassir.taxi.api.response;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 23.07.2016.
 */
public class OrderVehicleResponse extends BaseResponse {

    @SerializedName("regNumber")
    private String regNumber;
    @SerializedName("modelName")
    private String modelName;
    @SerializedName("photo")
    private String photo;
    @SerializedName("driverName")
    private String driverName;

    @NonNull
    public String getRegNumber() {
        if (regNumber == null){
            regNumber = "";
        }
        return regNumber;
    }

    @NonNull
    public String getModelName() {
        if (modelName == null){
            modelName = "";
        }
        return modelName;
    }

    @NonNull
    public String getPhoto() {
        if (photo == null){
            photo = "";
        }
        return photo;
    }

    @NonNull
    public String getDriverName() {
        if (driverName == null){
            driverName = "";
        }
        return driverName;
    }
}
