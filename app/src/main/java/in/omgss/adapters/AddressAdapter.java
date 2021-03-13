package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.interfaces.AddressItemClickListener;
import in.omgss.pojo.responses.addresslist.Response;
import in.omgss.ui.cart.CartActivity;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ItemsViewholder> {

    private final ArrayList<Response> addressList;
    private final AddressItemClickListener clickListener;
    private boolean hideOptions;
    private Context mContext;


    public AddressAdapter(ArrayList<Response> addressList, String from, AddressItemClickListener clickListener) {
        this.addressList = addressList;
        this.clickListener = clickListener;
        hideOptions = (from != null && from.equalsIgnoreCase(CartActivity.class.getName()));
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_address, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        Response data = addressList.get(position);
        if (data != null) {
            holder.tvAddressType.setText(data.getAddressprofilename());

            StringBuilder address = new StringBuilder();
            address.append(data.getAddress());

            if (data.getCity() != null && !data.getCity().isEmpty()) {
                address.append(", ");
                address.append(data.getCity());
            }

            if (data.getState() != null && !data.getState().isEmpty()) {
                address.append(", ");
                address.append(data.getState());
            }

            address.append("\n");
            address.append(data.getZip());

            holder.tvAddress.setText(address.toString());

            if (hideOptions) {
                holder.llOptions.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_address_type)
        TextView tvAddressType;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.ll_options)
        LinearLayout llOptions;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onAddressSelected(getAdapterPosition());
                }
            });

        }

        @OnClick({R.id.iv_edit, R.id.iv_delete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.iv_edit:
                    clickListener.onEditClicked(getAdapterPosition());
                    break;

                case R.id.iv_delete:
                    clickListener.onDeleteClicked(getAdapterPosition());
                    break;
            }
        }

    }
}
