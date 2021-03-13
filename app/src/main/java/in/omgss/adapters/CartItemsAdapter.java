package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.interfaces.CartItemClickListener;
import in.omgss.pojo.responses.cartresponse.Orderdetail;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ItemsViewholder> {

    private final ArrayList<Orderdetail> cartList;
    private final CartItemClickListener clickListener;
    private Context context;


    public CartItemsAdapter(ArrayList<Orderdetail> cartList, CartItemClickListener clickListener) {
        this.cartList = cartList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(context).inflate(R.layout.list_item_cart_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        Orderdetail data = cartList.get(position);

        if (data != null) {

            String imageUrl;
            if (data.getThumbnail() != null)
                imageUrl = data.getThumbnail();
            else
                imageUrl = data.getImage();

            holder.sdvItemImage.setImageURI(imageUrl);

            holder.tvItemName.setText(data.getProductname());

            holder.tvQuantity.setText(String.valueOf(data.getQuantity()));

            holder.tvPrice.setText(context.getString(R.string.currency));
            holder.tvPrice.append(String.format("%.2f", data.getActualprice()));
        }
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }


    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_image)
        SimpleDraweeView sdvItemImage;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.aiv_minus)
        AppCompatImageView aivMinus;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.aiv_plus)
        AppCompatImageView aivPlus;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.aiv_minus, R.id.aiv_plus})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.aiv_minus:
                    if (cartList.get(getAdapterPosition()).getQuantity() == 1) {
                        if (clickListener != null) {
                            clickListener.handleDeleteCartItem(getAdapterPosition());
                        }

                    } else {
                        int qty = cartList.get(getAdapterPosition()).getQuantity() - 1;
                        cartList.get(getAdapterPosition()).setQuantity(qty);
                        tvQuantity.setText(String.valueOf(qty));

                        if (clickListener != null) {
                            clickListener.onQuantityUpdate();
                        }
                    }
                    break;

                case R.id.aiv_plus:
                    if (cartList.get(getAdapterPosition()).getQuantity() < 10) {
                        int qty = cartList.get(getAdapterPosition()).getQuantity() + 1;
                        cartList.get(getAdapterPosition()).setQuantity(qty);
                        tvQuantity.setText(String.valueOf(qty));

                        if (clickListener != null) {
                            clickListener.onQuantityUpdate();
                        }
                    }
                    break;
            }
        }
    }
}
