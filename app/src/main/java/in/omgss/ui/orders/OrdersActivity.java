package in.omgss.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.AppViewPagerAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.pojo.ViewPagerModel;
import in.omgss.pojo.responses.myorders.OrdersListResponse;
import in.omgss.pojo.responses.myorders.Response;
import in.omgss.ui.orderdetail.OrderDetailActivity;
import okhttp3.MultipartBody;


public class OrdersActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.ll_tabs)
    LinearLayout llTabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_active_orders)
    TextView tvActiveOrders;
    @BindView(R.id.tv_past_orders)
    TextView tvPastOrders;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private AppViewPagerAdapter mPagerAdapter;

    private OrdersViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("My Orders");

        mViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setUpPagerAdapter();

        setObservers();
        getOrdersList();
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_orders;
    }

    /**
     * method to setup View Pager Adapter
     */
    private void setUpPagerAdapter() {
        if (mPagerAdapter == null) {
            ArrayList<ViewPagerModel> mFragmentList = new ArrayList<>();
            mFragmentList.add(new ViewPagerModel(new ActiveOrdersFragment(), ""));
            mFragmentList.add(new ViewPagerModel(new PastOrdersFragment(), ""));


            mPagerAdapter = new AppViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

            viewPager.setAdapter(mPagerAdapter);
            viewPager.setOffscreenPageLimit(mFragmentList.size() - 1);

            viewPager.addOnPageChangeListener(this);
        }
    }


    private void setObservers() {

        mViewModel.getOrdersLiveData().observe(this, new Observer<OrdersListResponse>() {
            @Override
            public void onChanged(OrdersListResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {

                    Fragment activeOrdersFragment = mPagerAdapter.getItem(0);
                    if (activeOrdersFragment instanceof ActiveOrdersFragment) {
                        ((ActiveOrdersFragment) activeOrdersFragment).setOrdersList(responseBody.getResponse());
                    }

                    Fragment pastOrdersFragment = mPagerAdapter.getItem(1);
                    if (pastOrdersFragment instanceof PastOrdersFragment) {
                        ((PastOrdersFragment) pastOrdersFragment).setOrdersList(responseBody.getResponse());
                    }

                }
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        unselectAll();
        switch (position) {
            case 0:
                tvActiveOrders.setBackgroundResource(R.drawable.drawable_left_selected);
                tvActiveOrders.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;

            case 1:
                tvPastOrders.setBackgroundResource(R.drawable.drawable_right_selected);
                tvPastOrders.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void unselectAll() {
        tvActiveOrders.setBackgroundResource(R.drawable.drawable_left_unselected);
        tvActiveOrders.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        tvPastOrders.setBackgroundResource(R.drawable.drawable_right_unsleected);
        tvPastOrders.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }


    @OnClick({R.id.tv_active_orders, R.id.tv_past_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_active_orders:
                viewPager.setCurrentItem(0);
                break;

            case R.id.tv_past_orders:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        finish();
    }


    /**
     * method to get orders list
     */
    public void getOrdersList() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody requestBody = getMultipartRequestBuilder()
                    .addFormDataPart("loggedinuserid", mViewModel.getUserId())
                    .build();

            mViewModel.getOrdersList(requestBody);

        } else {
            showNoNetworkError();
        }
    }


    /**
     * method to open order details
     */
    public void viewOrderDetails(Response orderData) {
        if (!isFinishing() && !isDestroyed() && orderData != null) {
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("data", orderData);
            startActivityForResult(intent, 11);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK) {
                    getOrdersList();
                }
                break;
        }
    }

}