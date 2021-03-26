package in.omgss.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.pojo.responses.categories.Response;
import in.omgss.ui.subcategories.CategoriesActivity;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ItemsViewholder> {

    private Context mContext;
    private String type;
    private final ArrayList<Response> mData;


    public CategoriesAdapter(ArrayList<Response> mData) {
        this.type = type;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_categories_home, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        holder.tvItemName.setText(mData.get(position).getName());
        holder.iv.setImageResource(R.drawable.dummy2);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        SimpleDraweeView iv;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, CategoriesActivity.class).putExtra("id", mData.get(getAdapterPosition()).getId()));
                }
            });
        }
    }
}
