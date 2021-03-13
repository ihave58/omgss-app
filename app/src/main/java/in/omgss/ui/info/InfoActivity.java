package in.omgss.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.constants.AppConstants;
import in.omgss.ui.careers.CareersActivity;
import in.omgss.ui.complain.ComplainActivity;
import in.omgss.ui.contactus.ContactUsActivity;
import in.omgss.ui.hireus.HireUsActivity;
import in.omgss.ui.support.SupportActivity;
import in.omgss.ui.webview.WebViewActivity;


public class InfoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Info");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_info;
    }

    @OnClick({R.id.iv_toolbar_left, R.id.cv_about_us, R.id.cv_careers, R.id.cv_complain, R.id.cv_support, R.id.cv_faq, R.id.cv_terms_conditions, R.id.cv_privacy_policy, R.id.cv_contact_us, R.id.cv_hire_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.cv_about_us:
                Intent intent1 = new Intent(InfoActivity.this, WebViewActivity.class);
                intent1.putExtra("url", AppConstants.ABOUT_US);
                intent1.putExtra("title", "About Us");
                startActivity(intent1);
                break;

            case R.id.cv_careers:
                Intent intent5 = new Intent(InfoActivity.this, CareersActivity.class);
                startActivity(intent5);
                break;

            case R.id.cv_complain:
                Intent intent6 = new Intent(InfoActivity.this, ComplainActivity.class);
                startActivity(intent6);
                break;

            case R.id.cv_support:
                Intent intent7 = new Intent(InfoActivity.this, SupportActivity.class);
                startActivity(intent7);
                break;

            case R.id.cv_faq:
                Intent intent4 = new Intent(InfoActivity.this, WebViewActivity.class);
                intent4.putExtra("url", AppConstants.FAQ);
                intent4.putExtra("title", "FAQ's");
                startActivity(intent4);
                break;

            case R.id.cv_terms_conditions:
                Intent intent3 = new Intent(InfoActivity.this, WebViewActivity.class);
                intent3.putExtra("url", AppConstants.TERMS_AND_CONDITIONS);
                intent3.putExtra("title", "Terms & Conditions");
                startActivity(intent3);
                break;

            case R.id.cv_privacy_policy:
                Intent intent2 = new Intent(InfoActivity.this, WebViewActivity.class);
                intent2.putExtra("url", AppConstants.PRIVACY_POLICY);
                intent2.putExtra("title", "Privacy Policy");
                startActivity(intent2);
                break;

            case R.id.cv_contact_us:
                Intent intent11 = new Intent(InfoActivity.this, ContactUsActivity.class);
                startActivity(intent11);
                break;

            case R.id.cv_hire_us:
                Intent intent12 = new Intent(InfoActivity.this, HireUsActivity.class);
                startActivity(intent12);
                break;
        }
    }

}