package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.interfaces.RecyclerItemClickListener;
import in.omgss.pojo.responses.myorders.Orderdetail;
import in.omgss.pojo.responses.myorders.Response;
import in.omgss.utils.AppUtils;

public class ActiveOrdersAdapter extends RecyclerView.Adapter<ActiveOrdersAdapter.ItemsViewholder> {
    private final ArrayList<Response> mOrderList;
    private final RecyclerItemClickListener clickListener;
    private Context mContext;


    public ActiveOrdersAdapter(ArrayList<Response> ordersList, RecyclerItemClickListener clickListener) {
        mOrderList = ordersList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_active_orders, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        Response data = mOrderList.get(position);

        holder.tvOrderId.setText(mContext.getText(R.string.order_id));
        holder.tvOrderId.append(data.getId());

        holder.tvOderDate.setText(mContext.getText(R.string.ordered_on));
        holder.tvOrderId.append(" ");
        holder.tvOderDate.append(AppUtils.formatDateTime(data.getDatetime(), "yyyy-MM-dd HH:mm:ss", "dd MMMM, yyyy"));

        holder.tvPrice.setText(mContext.getText(R.string.currency));
        holder.tvPrice.append(String.format("%.2f", data.getTotalordervalue()));

        StringBuilder items = new StringBuilder();
        for (Orderdetail orderedItems : data.getOrderdetails()) {
            if (!items.toString().isEmpty()) {
                items.append(", ");
            }
            items.append(orderedItems.getProductname());
        }

        holder.tvItems.setText(items.toString());

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_oder_date)
        TextView tvOderDate;
        @BindView(R.id.tv_label_items)
        TextView tvLabelItems;
        @BindView(R.id.tv_items)
        TextView tvItems;
        @BindView(R.id.tv_view_details)
        TextView tvViewDetails;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }
}
