package in.omgss.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.interfaces.VerificationDialogInterface;

/**
 * Created by Rupinder Kaur on 21/12/18.
 */
public class VerificationSuccessfulDialog extends Dialog {

    private Context context;
    private VerificationDialogInterface verificationDialogInterface;
    private String message;

    public VerificationSuccessfulDialog(Context context, VerificationDialogInterface verificationDialogInterface, String message) {
        super(context);
        this.context = context;
        this.verificationDialogInterface = verificationDialogInterface;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_verification_succesful);
        ButterKnife.bind(this);

        WindowManager.LayoutParams wlp = getWindow().getAttributes();

        getWindow().setAttributes(wlp);
        getWindow().setDimAmount(0.5f);
        getWindow().setBackgroundDrawable(null);
        getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;

        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        ((TextView) findViewById(R.id.tv_verification_succesful)).setText(message);
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        verificationDialogInterface.OnDoneClick();
    }
}
