package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.interfaces.ProductItemClickListener;
import in.omgss.pojo.responses.categories.Response;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewholder> {

    private Context mContext;
    private final ArrayList<Response> mData;
    private final ProductItemClickListener clickListener;

    public ItemsAdapter(ArrayList<Response> response, ProductItemClickListener clickListener) {
        mData = response;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        holder.tvHeader.setText(mData.get(position).getName());

        ListItemsAdapter mAdapter = new ListItemsAdapter(mData.get(position).getProducts(), position, clickListener);
        holder.rv.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.rv.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView tvHeader;
        @BindView(R.id.rv)
        RecyclerView rv;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
