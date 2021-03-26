package in.omgss.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.interfaces.DialogCallback;

/**
 * Created by Rupinder Kaur on 21/12/18.
 */
public class CustomAlerDialog extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_negative)
    TextView tvNegative;
    @BindView(R.id.tv_positive)
    TextView tvPositive;
    @BindView(R.id.view)
    View view;

    private final Context context;
    private final DialogCallback dialogCallback;
    private final String message;
    private final String positiveButtonText;
    private final String negativeButtonText;
    private final String title;

    public CustomAlerDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, DialogCallback dialogCallback) {
        super(context);
        this.context = context;
        this.dialogCallback = dialogCallback;
        this.message = message;
        this.title = title;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);
        ButterKnife.bind(this);
        tvPositive.setText(positiveButtonText);
        if (negativeButtonText.equals("")) {
            view.setVisibility(View.GONE);
            tvNegative.setVisibility(View.GONE);
        } else
            tvNegative.setText(negativeButtonText);
        tvTitle.setText(title);
        tvMessage.setText(message);
        ButterKnife.bind(this);
        getWindow().setDimAmount(0.5f);
        getWindow().setBackgroundDrawable(null);
        getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
    }

    @OnClick({R.id.tv_negative, R.id.tv_positive})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {

            case R.id.tv_negative:
                dialogCallback.onCancel();
                break;
            case R.id.tv_positive:
                dialogCallback.onSubmit(null, null);
                break;
        }
    }
}
