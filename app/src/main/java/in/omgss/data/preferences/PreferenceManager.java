package in.omgss.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static PreferenceManager instance;
    private final SharedPreferences sharedPref;

    private PreferenceManager(Context context) {
        sharedPref = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceManager init(Context context) {
        if (instance == null) {
            synchronized (PreferenceManager.class) {
                if (instance == null)
                    instance = new PreferenceManager(context);
            }
        }
        return instance;
    }


    /**
     * {@link #init(Context)} is called first
     *
     * @return instance
     */
    public static PreferenceManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }


    public int getInt(String key) {
        return (int) sharedPref.getLong(key, 0);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public String getString(String key) {
        return sharedPref.getString(key, "");
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void clearAllPrefs() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

}
