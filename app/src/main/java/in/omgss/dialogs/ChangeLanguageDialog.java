package in.omgss.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.interfaces.DialogCallback;

/**
 * Created by Rupinder Kaur on 21/12/18.
 */
public class ChangeLanguageDialog extends Dialog {

    private final Context context;
    private final DialogCallback dialogCallback;

    public ChangeLanguageDialog(Context context, DialogCallback dialogCallback) {
        super(context);
        this.context = context;
        this.dialogCallback = dialogCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_language);
        ButterKnife.bind(this);

        getWindow().setDimAmount(0.5f);
        getWindow().setBackgroundDrawable(null);
        getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

    }

    @OnClick(R.id.btn_verify)
    public void onViewClicked() {
        dismiss();
    }

}
