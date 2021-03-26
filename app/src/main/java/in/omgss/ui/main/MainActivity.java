package in.omgss.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.BuildConfig;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.constants.AppConstants;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.DialogCallback;
import in.omgss.ui.cart.CartActivity;
import in.omgss.ui.info.InfoActivity;
import in.omgss.ui.main.account.AccountsFragment;
import in.omgss.ui.main.categories.CategoriesFragment;
import in.omgss.ui.main.home.HomeFragment;
import in.omgss.ui.main.mydevices.MyDevicesFragment;
import in.omgss.ui.notifications.NotificationsActivity;
import in.omgss.ui.offers.OffersActivty;
import in.omgss.ui.settings.SettingsActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_cart_count)
    TextView tvCartCount;
    @BindView(R.id.iv_toolbar_left)
    ImageView ivToolbarLeft;
    @BindView(R.id.tv_notification_count)
    TextView tvNotificationCount;
    @BindView(R.id.fl_notification)
    FrameLayout flNotification;

    private ArrayList<Fragment> mFragmentList;
    private Fragment selectedFragment;


    // updating cart batch count
    private final BroadcastReceiver batchCountUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isFinishing() && !isDestroyed()) {

                int cartCount = DataManager.getInstance().getIntFromPreference(PreferenceConstants.CART_COUNT);
                if (cartCount > 0) {
                    tvCartCount.setText(String.valueOf(cartCount));
                    tvCartCount.setVisibility(View.VISIBLE);

                } else {
                    tvCartCount.setVisibility(View.INVISIBLE);
                }


                int notificationCount = DataManager.getInstance().getIntFromPreference(PreferenceConstants.NOTIFICATION_COUNT);
                if (notificationCount > 0) {
                    tvNotificationCount.setText(String.valueOf(notificationCount));
                    tvNotificationCount.setVisibility(View.VISIBLE);

                } else {
                    tvNotificationCount.setVisibility(View.INVISIBLE);
                }
            }
        }
    };


    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setListeners();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tvUserName.setText("Welcome ");
        tvUserName.append(DataManager.getInstance().getStringFromPreference(PreferenceConstants.FIRST_NAME));

        tvVersion.setText("Version ");
        tvVersion.append(BuildConfig.VERSION_NAME);

        LocalBroadcastManager.getInstance(this).registerReceiver(batchCountUpdateReceiver, new IntentFilter(AppConstants.FILTER_UPDATE_BATCH_COUNT));
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mFragmentList = new ArrayList<>();
        selectedFragment = new HomeFragment();
        mFragmentList.add(selectedFragment);
        mFragmentList.add(new MyDevicesFragment());
        mFragmentList.add(new CategoriesFragment());
        mFragmentList.add(new AccountsFragment());

        for (int i = 0; i < mFragmentList.size(); i++) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_home_container, mFragmentList.get(i), mFragmentList.get(i).getClass().getName());
            if (i == 0)
                fragmentTransaction.show(mFragmentList.get(i));
            else
                fragmentTransaction.hide(mFragmentList.get(i));
            fragmentTransaction.commit();
        }

        getCartCount();
        getNotificationCount();

        new ConfirmationDialog(this,
                "Location Alert!",
                "We currently operate in Raipur location only. Please proceed only if you reside in this location.\n",
                "Proceed",
                "Exit",
                new DialogCallback() {
                    @Override
                    public void onSubmit(View view, Object result) {
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }
                })
                .show();

    }


    /**
     * method to setup Listeners
     */
    private void setListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        if (!(selectedFragment instanceof HomeFragment)) {

                            tvTitle.setText(getString(R.string.app_name));

                            tvTitle.setVisibility(View.GONE);
                            ivLogo.setVisibility(View.VISIBLE);

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.hide(selectedFragment);
                            selectedFragment = mFragmentList.get(0);
                            fragmentTransaction.show(selectedFragment);
                            fragmentTransaction.commit();
                        }
                        break;

                    case R.id.navigation_orders:
                        if (!(selectedFragment instanceof MyDevicesFragment)) {

                            tvTitle.setText("My Devices");
                            tvTitle.setVisibility(View.VISIBLE);
                            ivLogo.setVisibility(View.GONE);

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.hide(selectedFragment);
                            selectedFragment = mFragmentList.get(1);
                            fragmentTransaction.show(selectedFragment);
                            fragmentTransaction.commit();
                        }
                        break;

                    case R.id.navigation_categories:
                        if (!(selectedFragment instanceof CategoriesFragment)) {

                            tvTitle.setText("Categories");
                            tvTitle.setVisibility(View.VISIBLE);
                            ivLogo.setVisibility(View.GONE);

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.hide(selectedFragment);
                            selectedFragment = mFragmentList.get(2);
                            fragmentTransaction.show(selectedFragment);
                            fragmentTransaction.commit();
                        }
                        break;

                    case R.id.navigation_account:
                        if (!(selectedFragment instanceof AccountsFragment)) {

                            tvTitle.setText("Account");
                            tvTitle.setVisibility(View.VISIBLE);
                            ivLogo.setVisibility(View.GONE);

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.hide(selectedFragment);
                            selectedFragment = mFragmentList.get(3);
                            fragmentTransaction.show(selectedFragment);
                            fragmentTransaction.commit();
                        }
                        break;


                }
                return true;
            }
        });
    }


    @OnClick({R.id.tv_info, R.id.tv_home, R.id.tv_offers, R.id.tv_settings, R.id.iv_toolbar_left, R.id.iv_logo, R.id.fl_notification, R.id.fl_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info:
                startActivity(new Intent(this, InfoActivity.class));
                toggleNavigationDrawer();
                break;

            case R.id.tv_home:
                if (!(selectedFragment instanceof HomeFragment)) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_home);
                }
                toggleNavigationDrawer();
                break;

            case R.id.tv_offers:
                toggleNavigationDrawer();
                startActivity(new Intent(this, OffersActivty.class));
                break;

            case R.id.tv_settings:
                toggleNavigationDrawer();
                startActivity(new Intent(this, SettingsActivity.class));

                break;

            case R.id.iv_logo:
                break;

            case R.id.iv_toolbar_left:
                toggleNavigationDrawer();
                break;

            case R.id.fl_notification:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;

            case R.id.fl_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
        }
    }


    /**
     * method to toggle Navigation Drawer
     */
    public void toggleNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.openDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (!(selectedFragment instanceof HomeFragment)) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);

        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        if (batchCountUpdateReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(batchCountUpdateReceiver);
        }
        super.onDestroy();
    }
}