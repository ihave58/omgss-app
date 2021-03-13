package in.omgss.ui.offers;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.OffersAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.pojo.responses.offers.OffersResponse;
import okhttp3.RequestBody;

public class OffersActivty extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_offers)
    RecyclerView rvAddresses;
    private OffersAdapter mAddressAdapter;
    private OffersViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Offers");
        mViewModel = new ViewModelProvider(this).get(OffersViewModel.class);

        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());
        mViewModel.getmResendOtpLiveData().observe(this, new Observer<OffersResponse>() {
            @Override
            public void onChanged(OffersResponse responseBody) {
                hideProgressDialog();
                mAddressAdapter = new OffersAdapter(responseBody.getResponse());
                rvAddresses.setLayoutManager(new LinearLayoutManager(OffersActivty.this, RecyclerView.VERTICAL, false));
                rvAddresses.setAdapter(mAddressAdapter);
            }
        });

        RequestBody body = getMultipartRequestBuilder()
                .build();
        mViewModel.login(body);

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_offers_activty;
    }

    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}