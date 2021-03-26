package in.omgss.adapters;

import android.content.Context;
import android.text.Html;
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
import in.omgss.pojo.responses.notifications.Response;
import in.omgss.utils.AppUtils;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ItemsViewholder> {


    private Context mContext;
    private final ArrayList<Response> mData;

    public NotificationsAdapter(ArrayList<Response> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ItemsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemsViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notifications, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewholder holder, int position) {
        holder.image.setImageURI(mData.get(position).getImage());

        holder.tvDescription.setText(Html.fromHtml(mData.get(position).getDescription()));

        holder.tvTime.setText(AppUtils.formatDateTime(mData.get(position).getDatetime(), "yyyy-MM-dd HH:mm:ss", "dd MMMM, yyyy | hh:mm a"));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ItemsViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ItemsViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
