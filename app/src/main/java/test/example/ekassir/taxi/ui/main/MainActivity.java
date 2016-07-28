package test.example.ekassir.taxi.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.view.View;
import android.widget.Button;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.example.ekassir.taxi.R;
import test.example.ekassir.taxi.api.RestClient;
import test.example.ekassir.taxi.api.response.BaseResponse;
import test.example.ekassir.taxi.api.response.OrderResponse;
import test.example.ekassir.taxi.ui.details.DetailsActivity;
import test.example.ekassir.taxi.ui.main.view.BaseViewHolder;
import test.example.ekassir.taxi.ui.main.view.OrdersListAdapter;

public class MainActivity extends AppCompatActivity implements OrdersListAdapter.OnItemClickListener,
        Callback<List<OrderResponse>>, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private OrdersListAdapter ordersListAdapter;

    public static final int CONTENT_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int PROGRESS_STATE = 3;

    private View contentLayout;
    private View errorLayout;
    private View progressLayout;
    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restClient = new RestClient();

        ordersListAdapter = new OrdersListAdapter(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentLayout = findViewById(R.id.activity_main_layout);
        errorLayout = findViewById(R.id.network_error_layout);
        progressLayout = findViewById(R.id.progress_layout);
        Button retryButton = (Button) findViewById(R.id.retry_button);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(ordersListAdapter);
        }
//        try {
//            InputStream is = getResources().getAssets().open("orders.json");
//            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            Type listType = new TypeToken<ArrayList<OrderResponse>>(){}.getType();
//            ArrayList<OrderResponse> response = new Gson().fromJson(br, listType);
//            ordersListAdapter.update(response);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (retryButton != null){
            retryButton.setOnClickListener(this);
        }

        requestOrders();
    }

    private void requestOrders(){
        setState(PROGRESS_STATE);
        restClient.getApi().getOrders().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
        if (response.isSuccessful()) {
            ordersListAdapter.update(response.body(), true);
            setState(CONTENT_STATE);
        }
        else {
            setState(ERROR_STATE);
        }
    }

    @Override
    public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
        setState(ERROR_STATE);
    }


    @Override
    public void onClick(View v) {
        requestOrders();
    }

    @Override
    public void onItemClick(BaseViewHolder viewHolder) {
        BaseResponse response = ordersListAdapter.getItem(viewHolder.getAdapterPosition());
        DetailsActivity.start(this, response);
    }


    private void setState(int state){
        errorLayout.setVisibility(View.GONE);
        contentLayout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
        switch (state){
            case CONTENT_STATE:
                contentLayout.setVisibility(View.VISIBLE);
                break;
            case ERROR_STATE:
                errorLayout.setVisibility(View.VISIBLE);
                break;
            case PROGRESS_STATE:
                progressLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

}
