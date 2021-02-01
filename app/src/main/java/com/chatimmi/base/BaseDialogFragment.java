package com.chatimmi.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {

    private BaseActivitykt baseActivity;
    private Context context;
    private boolean isFullScreen = false;
    private int style = -1;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        this.baseActivity = (BaseActivitykt) context;
        super.onAttach(context);
    }

    protected final void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    protected final void setStyle(@StyleRes int style) {
        this.style = style;
    }

    protected final BaseActivitykt getBaseActivity() {
        return baseActivity;
    }
}
