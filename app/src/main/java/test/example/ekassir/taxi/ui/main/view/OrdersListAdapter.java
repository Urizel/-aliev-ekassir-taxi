package test.example.ekassir.taxi.ui.main.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import test.example.ekassir.taxi.R;
import test.example.ekassir.taxi.api.response.BaseResponse;
import test.example.ekassir.taxi.api.response.OrderResponse;

/**
 * Created on 23.07.2016.
 */
public class OrdersListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public OrderResponse getItem(int position) {
        return responseList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseViewHolder viewHolder);
    }

    private List<OrderResponse> responseList;
    private OnItemClickListener onItemClickListener;

    public OrdersListAdapter(@Nullable OnItemClickListener onItemClickListener) {
        super();
        responseList = new LinkedList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_main_list_item, parent, false);
        return new OrderViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        OrderResponse response = responseList.get(position);
        holder.update(response);
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void update(@NonNull List<OrderResponse> responseList){
        update(responseList, false);
    }

    public void update(@NonNull List<OrderResponse> responseList, boolean needSort) {
        this.responseList = responseList;
        if (needSort) {
            Collections.sort(responseList, new Comparator<OrderResponse>() {
                @Override
                public int compare(OrderResponse lhs, OrderResponse rhs) {
                    if (lhs.getMillis() < rhs.getMillis()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
        notifyDataSetChanged();
    }
}

