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
public class ConfirmationDialog extends Dialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private final Context context;
    private final DialogCallback dialogCallback;
    private final String title;
    private final String message;
    private final String positve;
    private final String negative;

    public ConfirmationDialog(Context context, String title, String message, String positive, String negative, DialogCallback dialogCallback) {
        super(context);
        this.context = context;
        this.title = title;
        this.message = message;
        this.positve = positive;
        this.negative = negative;
        this.dialogCallback = dialogCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirmation);
        ButterKnife.bind(this);

        tvTitle.setText(title);
        tvMessage.setText(message);
        tvCancel.setText(negative);
        tvDelete.setText(positve);

        if (getWindow() != null) {
            getWindow().setDimAmount(0.5f);
            getWindow().setBackgroundDrawable(null);
            getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.CENTER);
            setCancelable(false);
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_delete})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_cancel:
                dialogCallback.onCancel();
                break;
            case R.id.tv_delete:
                dialogCallback.onSubmit(null, null);
                break;
        }
    }

}
