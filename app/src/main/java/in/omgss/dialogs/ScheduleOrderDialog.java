package in.omgss.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.ScheduleTimeAdapter;
import in.omgss.interfaces.RecyclerItemClickListener;
import in.omgss.interfaces.ScheduleOrderCallBack;


public class ScheduleOrderDialog extends BottomSheetDialog {

    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.tv_schedule_date)
    TextView tvScheduleDate;
    @BindView(R.id.rv_schedule_time)
    RecyclerView rvScheduleTime;

    private final Calendar minCal = Calendar.getInstance();
    private final Calendar maxCal = Calendar.getInstance();

    private final ScheduleOrderCallBack scheduleOrderCallBack;
    private String selectedTime;
    private DatePickerDialog datePickerDialog;

    private final ArrayList<String> mTimeList = new ArrayList<>();
    private ScheduleTimeAdapter mTimeAdapter;


    public ScheduleOrderDialog(@NonNull Context context, ScheduleOrderCallBack scheduleOrderCallBack) {
        super(context);
        this.scheduleOrderCallBack = scheduleOrderCallBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule_order, null);
        setContentView(view);
        ButterKnife.bind(this);

        String date = minCal.get(Calendar.DAY_OF_MONTH) + "-" + (minCal.get(Calendar.MONTH) + 1) + "-" + minCal.get(Calendar.YEAR);
        tvScheduleDate.setText(date);

        setTime();

        if (getWindow() != null) {
            getWindow().setDimAmount(0.5f);
            getWindow().setBackgroundDrawable(null);
            getWindow().setGravity(Gravity.BOTTOM);
//            setCancelable(false);

            if (mTimeList.size() > 4) {
                int height = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_400dp);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
                BottomSheetBehavior.from((View) view.getParent()).setPeekHeight(height);
            } else {
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }

    }

    private void setTime() {
        mTimeList.clear();
        mTimeList.add("11:00 AM");
        mTimeList.add("12:00 PM");
        mTimeList.add("01:00 PM");
        mTimeList.add("02:00 PM");
        mTimeList.add("03:00 PM");
        mTimeList.add("04:00 PM");
        mTimeList.add("05:00 PM");
        mTimeList.add("06:00 PM");

        mTimeAdapter = new ScheduleTimeAdapter(mTimeList, new RecyclerItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                selectedTime = mTimeList.get(position);
            }
        });
        rvScheduleTime.setAdapter(mTimeAdapter);
    }

    @OnClick({R.id.tv_schedule_date, R.id.aiv_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_schedule_date:
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                            tvScheduleDate.setText(date);

                        }
                    }, minCal.get(Calendar.YEAR), minCal.get(Calendar.MONTH), minCal.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());

                    maxCal.add(Calendar.DAY_OF_MONTH, +6);
                    datePickerDialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());
                }
                datePickerDialog.show();
                break;

            case R.id.aiv_done:
                if (selectedTime == null) {
                    Toast.makeText(getContext(), "Please select time", Toast.LENGTH_SHORT).show();

                } else {
                    dismiss();
                    if (scheduleOrderCallBack != null)
                        scheduleOrderCallBack.onScheduleSelected(tvScheduleDate.getText().toString(), selectedTime);
                }
                break;
        }
    }

}
