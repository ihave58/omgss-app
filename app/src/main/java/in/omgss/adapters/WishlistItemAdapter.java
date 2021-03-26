package in.omgss.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.interfaces.ProductItemClickListener;
import in.omgss.pojo.responses.wishlist.Orderdetail;

public class WishlistItemAdapter extends RecyclerView.Adapter<WishlistItemAdapter.ItemsViewholder> {

    private Context mContext;
    private final ProductItemClickListener clickListener;
    private final ArrayList<Orderdetail> mData;
    private int position = 0;


    public WishlistItemAdapter(ArrayList<Orderdetail> products, int position, ProductItemClickListener clickListener) {
        this.mData = products;
        this.position = position;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_items, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        String imageUrl;
        if (mData.get(position).getThumbnail() != null)
            imageUrl = mData.get(position).getThumbnail();
        else
            imageUrl = mData.get(position).getImage();

        holder.ivItem.setImageURI(imageUrl);

        holder.tvItemName.setText(mData.get(position).getProductname());

        holder.tvdiscountprice.setText(mContext.getString(R.string.currency));
        holder.tvdiscountprice.append(String.valueOf(mData.get(position).getSaleprice()));

        holder.tvOriginal.setText(mContext.getString(R.string.currency));
        holder.tvOriginal.append(String.valueOf(mData.get(position).getActualprice()));
        holder.tvOriginal.setPaintFlags(holder.tvOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (mData.get(position).getDiscount() != null && !mData.get(position).getDiscount().isEmpty()) {
            holder.tvDiscount.setText(mData.get(position).getDiscount());
            holder.tvDiscount.append(" discount");
            holder.tvDiscount.setVisibility(View.VISIBLE);

        } else {
            holder.tvDiscount.setVisibility(View.GONE);
        }

        if (mData.get(position).getIsAddedToCart() == 1) {
            holder.btnDone.setText("Remove");
            holder.btnDone.setBackgroundResource(R.drawable.drawable_grey_btn_bg);

        } else {
            holder.btnDone.setText("Add");
            holder.btnDone.setBackgroundResource(R.drawable.drawable_green_btn_bg_rounded);
        }

        holder.ivHeart.setImageResource(R.drawable.ic_heart_filled);

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_heart)
        ImageView ivHeart;
        @BindView(R.id.iv_item)
        SimpleDraweeView ivItem;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_original)
        TextView tvOriginal;
        @BindView(R.id.tvdiscountprice)
        TextView tvdiscountprice;
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.tvDiscount)
        TextView tvDiscount;
        @BindView(R.id.btn_done)
        TextView btnDone;

        ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClicked(position, getAdapterPosition());

                    }
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {

                        clickListener.onAddToCartClicked(position, getAdapterPosition());

                        if (mData.get(getAdapterPosition()).getIsAddedToCart() == 0) {
                            mData.get(getAdapterPosition()).setIsAddedToCart(1);
                            btnDone.setText("Remove");
                            btnDone.setBackgroundResource(R.drawable.drawable_grey_btn_bg);

                        } else {
                            mData.get(getAdapterPosition()).setIsAddedToCart(0);
                            btnDone.setText("Add");
                            btnDone.setBackgroundResource(R.drawable.drawable_green_btn_bg_rounded);
                        }


                    }
                }
            });

            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onAddToWishListClicked(position, getAdapterPosition());
                    }
                }
            });
        }
    }

}