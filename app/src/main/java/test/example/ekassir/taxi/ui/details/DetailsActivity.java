package test.example.ekassir.taxi.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import test.example.ekassir.taxi.R;
import test.example.ekassir.taxi.api.IEKassirApi;
import test.example.ekassir.taxi.api.response.BaseResponse;
import test.example.ekassir.taxi.api.response.OrderResponse;
import test.example.ekassir.taxi.utils.imageloader.SimpleImageLoader;

/**
 * Created on 24.07.2016.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        OrderResponse response = (OrderResponse)getIntent().getSerializableExtra(EXTRA_RESPONSE);
        if (response == null){
            Log.e(TAG, "nullable extra " + EXTRA_RESPONSE);
            return;
        }

        ImageView imageView = (ImageView)findViewById(R.id.vehicle_image);
        TextView startAddressText = (TextView)findViewById(R.id.start_address_text);
        TextView endAddressText = (TextView)findViewById(R.id.end_address_text);
        TextView driverText = (TextView)findViewById(R.id.driver_text);
        TextView priceText = (TextView)findViewById(R.id.price_text);
        TextView regNumberText = (TextView)findViewById(R.id.reg_number_text);
        TextView orderDateText = (TextView)findViewById(R.id.order_date_text);
        TextView modelText = (TextView)findViewById(R.id.model_text);

        SimpleImageLoader simpleImageLoader = new SimpleImageLoader(getApplicationContext());
        if (imageView != null) {
            String url = IEKassirApi.IMAGES_PATH + response.getVehicle().getPhoto();

            int widthPixels = getResources().getDisplayMetrics().widthPixels;

            simpleImageLoader.load(url, imageView, widthPixels, -1);
        }

        if (modelText != null){
            modelText.setText(response.getVehicle().getModelName());
        }
        if (startAddressText != null) {
            String startAddress = response.getStartAddress().getCity() + ", "
                    + response.getStartAddress().getAddress();
            startAddressText.setText(startAddress);
        }
        if (endAddressText != null) {
            String endAddress = response.getEndAddress().getCity() + ", "
                    + response.getEndAddress().getAddress();
            endAddressText.setText(endAddress);
        }
        if (driverText != null) {
            driverText.setText(response.getVehicle().getDriverName());
        }
        if (priceText != null) {
            priceText.setText(response.getFormattedPrice());
        }
        if (regNumberText != null) {
            regNumberText.setText(response.getVehicle().getRegNumber());
        }
        if (orderDateText != null) {
            orderDateText.setText(response.getFormattedOrderDate());
        }
    }

    public static void start(@NonNull Context context, @NonNull BaseResponse response) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_RESPONSE, response);
        context.startActivity(intent);
    }
}
