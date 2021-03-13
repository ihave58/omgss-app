package in.omgss.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppUtils {


    /**
     * method to copy text to clipboard
     */
    public static void copyTextToClipboard(Context context, String text) {
        if (context != null) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(context.getPackageName(), text);
            if (clipboard == null || clip == null)
                return;

            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     * method to get Address NotificationList from location
     */
    public static List<Address> getAddressFromLocation(AppCompatActivity mActivity, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(mActivity);
        List<Address> address = null;
        Log.d("LatLng:", latitude + ", " + longitude);
        try {
            if (latitude != 0 && longitude != 0)
                address = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
            }
        }
        return false;
    }

    public static void addFragment(AppCompatActivity mActivity, Fragment fragment, int container) {
        FragmentTransaction mFragmentTransaction = (mActivity.getSupportFragmentManager()).beginTransaction();
        mFragmentTransaction.add(container, fragment, "tag");
        mFragmentTransaction.commitAllowingStateLoss();
    }


    public static void hideKeyboard(Activity context) {
        if (context != null && context.getCurrentFocus() != null) {
            try {
                // use application level context to avoid unnecessary leaks.
                InputMethodManager inputManager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null)
                    inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showKeyboard(Activity context) {
        if (context != null && context.getCurrentFocus() != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.showSoftInput(context.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("HardwareIds")
    public static String getUniqueDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Gets the version name of the application. For e.g. 1.9.3
     **/
    public static String getApplicationVersionNumber(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * Gets the version code of the application. For e.g. Maverick Meerkat or 2013050301
     **/
    public static int getApplicationVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * Gets the version number of the Android OS For e.g. 2.3.4 or 4.1.2
     **/
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * method to open playstore
     *
     * @param context
     */
    public static void openPlayStore(Context context) {
        if (context != null) {
            String appId = context.getPackageName();
            Uri uri = Uri.parse("market://details?id=" + appId);
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                context.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appId)));
            }
        }
    }

    public static void openUrl(Context mContext, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(Intent.createChooser(intent, "Open With"));
        } else {
            Toast.makeText(mContext, "No Suitable Application Found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * method to be used for sharing the app or inviting friends
     *
     * @param context context of the activity
     * @param subject extra subject for example :- Share Goodlife
     * @param text    text to be shared
     * @param title   purpose of the intent chooser
     */

    public static void shareTextWithChooser(Context context, String subject, String text, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static String formatDateTime(String input, String inputFormat, String outputFormat) {
        SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        try {
            Date date = inFormat.parse(input);
            if (date != null)
                return outFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap getBitmapFromImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    public static Bitmap getImageBitmapFromUrl(String imageUrl) {
        Bitmap bm = null;
        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inSampleSize = 5;
                bm = BitmapFactory.decodeStream(bis, null, options);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static void setClickableSpan(SpannableString spannableString, String string, String substring, final boolean shouldUnderline, TextView textView, final IClickableSpanListener listener) {
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                listener.onClick();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(shouldUnderline);
            }
        };

        spannableString.setSpan(clickableSpan, string.indexOf(substring), string.indexOf(substring) + substring.length(), 0);
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void setColorSpan(SpannableString spannableString, String string, String substring, int color) {
        spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(substring), string.indexOf(substring) + substring.length(), 0);
    }
}
