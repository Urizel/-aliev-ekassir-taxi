package test.example.ekassir.taxi.ui.main.view;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import test.example.ekassir.taxi.R;
import test.example.ekassir.taxi.api.response.BaseResponse;
import test.example.ekassir.taxi.api.response.OrderResponse;

/**
 * Created on 27.07.2016.
 */
class OrderViewHolder extends BaseViewHolder {

    private TextView startStreetText;
    private TextView endStreetText;
    private TextView startCityText;
    private TextView endCityText;
    private TextView timeText;
    private TextView priceText;

    public OrderViewHolder(View itemView,
                           @Nullable OrdersListAdapter.OnItemClickListener onItemClickListener) {
        super(itemView, onItemClickListener);

        startStreetText = (TextView)itemView.findViewById(R.id.start_street_text);
        endStreetText = (TextView)itemView.findViewById(R.id.end_street_text);
        startCityText = (TextView)itemView.findViewById(R.id.start_city_text);
        endCityText = (TextView)itemView.findViewById(R.id.end_city_text);
        timeText = (TextView)itemView.findViewById(R.id.time_text);
        priceText = (TextView)itemView.findViewById(R.id.price_text);
    }

    @Override
    public void update(BaseResponse response) {
        OrderResponse orderResponse = (OrderResponse)response;
        startStreetText.setText(orderResponse.getStartAddress().getAddress());
        endStreetText.setText(orderResponse.getEndAddress().getAddress());
        startCityText.setText(orderResponse.getStartAddress().getCity());
        endCityText.setText(orderResponse.getEndAddress().getCity());
        priceText.setText(orderResponse.getFormattedPrice());
        timeText.setText(orderResponse.getFormattedDate());
    }
}
