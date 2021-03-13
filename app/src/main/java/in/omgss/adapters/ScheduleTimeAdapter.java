package in.omgss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.interfaces.RecyclerItemClickListener;

public class ScheduleTimeAdapter extends RecyclerView.Adapter<ScheduleTimeAdapter.TimeViewHolder> {

    private Context context;
    private ArrayList<String> mTimeList;
    private RecyclerItemClickListener itemClickListener;
    private int selectedPosition = -1;

    public ScheduleTimeAdapter(ArrayList<String> mTimeList, RecyclerItemClickListener itemClickListener) {
        this.mTimeList = mTimeList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TimeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_schedule_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.atvTime.setText(mTimeList.get(position));

        if (position == selectedPosition) {
            holder.atvTime.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.atvTime.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            holder.atvTime.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            holder.atvTime.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

    }

    @Override
    public int getItemCount() {
        return mTimeList.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.atv_time)
        AppCompatTextView atvTime;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClicked(getAdapterPosition());

                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });

        }
    }

}
