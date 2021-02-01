package com.chatimmi.app.pref;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class prefHelper {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private String stripeCustomerId = "stripeCustomerId";
    private String alert_status = "alert_status";
    private static prefHelper sInstance;
    private static Context _context;

    public static synchronized prefHelper instance() {
        return sInstance;
    }

    @SuppressLint("CommitPrefEdits")
    public prefHelper(Context context) {

        _context = context;
        sInstance = this;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void init(Context context) {
        if (sInstance == null) {
            new prefHelper(context);
        }
    }


    private void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public boolean remove(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
            return true;
        }
        return false;
    }






    public void removeAll() {
        editor.clear().commit();
    }

    public void savePref(String key, Object value) {
        delete(key);
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }



}

