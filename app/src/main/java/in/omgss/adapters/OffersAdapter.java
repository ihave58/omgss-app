package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.pojo.responses.offers.Response;
import in.omgss.utils.AppUtils;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ItemsViewholder> {


    private Context mContext;
    private final ArrayList<Response> mData;

    public OffersAdapter(ArrayList<Response> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offer, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        holder.tv3.setText("Use Code - " + mData.get(position).getCouponcode());

        holder.tv1.setText(mData.get(position).getCouponname());

        switch (mData.get(position).getCoupontype()) {
            case "Flat":
                holder.tv2.setText("*Get Flat " + mContext.getString(R.string.currency) + mData.get(position).getCouponamount() + " off on your order.");
                break;

            default:
                holder.tv2.setText("*Get " + mData.get(position).getCouponamount() + "% off on your order.");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv3)
        TextView tv3;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.copyTextToClipboard(mContext, mData.get(getAdapterPosition()).getCouponcode());
                    Toast.makeText(mContext, "Coupon Code copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
