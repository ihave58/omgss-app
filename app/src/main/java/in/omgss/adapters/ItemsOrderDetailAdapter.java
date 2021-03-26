package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.pojo.responses.myorders.Orderdetail;

public class ItemsOrderDetailAdapter extends RecyclerView.Adapter<ItemsOrderDetailAdapter.ItemsViewholder> {

    private final List<Orderdetail> orderList;
    private Context mContext;

    public ItemsOrderDetailAdapter(List<Orderdetail> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_detail, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        String imageUrl;
        if (orderList.get(position).getThumbnail() != null)
            imageUrl = orderList.get(position).getThumbnail();
        else
            imageUrl = orderList.get(position).getProductimg();

        holder.sdvItemImage.setImageURI(imageUrl);

        holder.tvItemName.setText(orderList.get(position).getProductname());

        holder.tvQuantity.setText("Quantity: ");
        holder.tvQuantity.append(String.valueOf(orderList.get(position).getQuantity()));

        holder.tvPrice.setText(mContext.getText(R.string.currency));
        holder.tvPrice.append(String.format("%.2f", orderList.get(position).getActualprice()));

    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_image)
        SimpleDraweeView sdvItemImage;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
