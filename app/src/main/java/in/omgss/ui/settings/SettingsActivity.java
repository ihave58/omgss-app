package in.omgss.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.DialogCallback;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.switch_notification)
    Switch switchNotification;
    @BindView(R.id.rl_notifications)
    RelativeLayout rlNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Settings");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_settings;
    }

    @OnClick({R.id.iv_toolbar_left, R.id.rl_notifications, R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;

            case R.id.rl_notifications:
                switchNotification.setChecked(!switchNotification.isChecked());
                break;


            case R.id.rl_logout:
                ConfirmationDialog customAlerDialog = new ConfirmationDialog(this, "Logout", "Are you sure you want to logout?", "Yes", "No",
                        new DialogCallback() {
                            @Override
                            public void onSubmit(View view, Object result) {
                                logout();
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                customAlerDialog.show();
        }
    }
}