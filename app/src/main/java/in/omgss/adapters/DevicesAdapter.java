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
import in.omgss.pojo.responses.mydevices.Response;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ItemsViewholder> {

    private final ArrayList<Response> devicesList;
    private Context mContext;
    private String type;

    public DevicesAdapter(ArrayList<Response> devicesList) {
        this.devicesList = devicesList;
    }


    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_devices, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        holder.tvDeviceId.setText(devicesList.get(position).getId());

        holder.tvDeviceName.setText(devicesList.get(position).getDevicename());

        holder.tvDaysLeft.setText(String.valueOf(devicesList.get(position).getDaysLeft()));
        holder.tvDaysLeft.append(" days");
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_device_id)
        TextView tvDeviceId;
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_days_left)
        TextView tvDaysLeft;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
