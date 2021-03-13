package in.omgss.ui.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.iv_toolbar_left)
    ImageView ivToolbarLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(getIntent().getStringExtra("url"));
        tvTitle.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_web_view;
    }

    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}