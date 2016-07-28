package test.example.ekassir.taxi.ui.main.view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import test.example.ekassir.taxi.api.response.BaseResponse;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OrdersListAdapter.OnItemClickListener listener;

    public BaseViewHolder(View itemView,
                          @Nullable OrdersListAdapter.OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(this);
        }
    }

    void update(BaseResponse response){
    }
}
