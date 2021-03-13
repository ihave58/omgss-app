package in.omgss.ui.main.account;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.ui.orders.OrdersActivity;
import in.omgss.ui.profile.ProfileActivity;
import in.omgss.ui.wishlist.WishlistActivity;

public class AccountsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.cv_wishlist, R.id.cv_orders, R.id.cv_whatsapp_us, R.id.cv_email_us, R.id.cv_invite_friends, R.id.cv_rate_us, R.id.cv_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_profile:
                startActivity(new Intent(getActivity(), ProfileActivity.class));

                break;
            case R.id.cv_wishlist:
                startActivity(new Intent(getActivity(), WishlistActivity.class));

                break;
            case R.id.cv_orders:
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                break;

            case R.id.cv_whatsapp_us:
                String url = "https://api.whatsapp.com/send?phone=" + "+918770772802";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.cv_email_us:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "sust.solns@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, ""));
                break;

            case R.id.cv_invite_friends:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Invite Link");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;


            case R.id.cv_rate_us:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
                break;

        }
    }
}
