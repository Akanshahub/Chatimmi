package com.chatimmi.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.chatimmi.app.pref.Constants;
import com.chatimmi.app.utils.KeyboardUtil;
import com.chatimmi.app.utils.ProgressDialog;
import com.chatimmi.app.utils.StackSet;

import org.jetbrains.annotations.NotNull;


public abstract class BaseActivity extends AppCompatActivity {
    private StackSet<Fragment> stackSet = new StackSet<>();
    private com.chatimmi.app.pref.prefHelper prefHelper;
    private ProgressDialog progressDialog;

    public Activity getActivity() {
        return this;
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null)
            KeyboardUtil.hideKeyboard(this, getCurrentFocus());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
    }

    public void toastMessage(String mssge, Context context) {
        Toast.makeText(context, mssge, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color) {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(color));
    }


    public final StackSet<Fragment> getBackStack() {
        return stackSet;
    }

    public final Fragment getCurrentFragment() {
        return stackSet.peek();
    }

    public final void replaceFragment(@NonNull Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            String fragmentName = fragment.getClass().getName();
            fm.beginTransaction().replace(containerId, fragment, fragmentName).commit();
            if (addToBackStack) stackSet.push(fragment);
            hideKeyboard();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public final void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public final <T> void navigateTo(Class<T> destination, Bundle bundle) {
        Intent intent = new Intent(this, destination);
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle);
        }
        startActivity(intent);
    }

    public final <T> void navigateTo(Class<T> destination, Bundle bundle, boolean isFinishing) {
        Intent intent = new Intent(this, destination);
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle);
        }
        startActivity(intent);
        if (isFinishing) finish();
    }

    public final void navigateTo(Intent intent, boolean isFinishing) {
        startActivity(intent);
        if (isFinishing) finish();
    }



    public void hideLoader() {
        progressDialog.dismiss();
    }

    public void showLoader() {
        if (!isFinishing()) {
            progressDialog.show();
        }
    }


}