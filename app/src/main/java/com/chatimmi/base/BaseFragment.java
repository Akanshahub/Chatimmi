package com.chatimmi.base;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.chatimmi.app.utils.StackSet;


public class BaseFragment extends Fragment {

    private StackSet<Fragment> stackSet = new StackSet<>();
    protected BaseActivitykt activity;

    @Override
    public void onAttach(@NonNull Context context) {
        this.activity = (BaseActivitykt) context;

        super.onAttach(context);
    }

    protected final StackSet<Fragment> getChildBackStack() {
        return stackSet;
    }

    protected final Fragment getCurrentChildFragment() {
        return stackSet.peek();
    }

    protected final void replaceChildFragment(@NonNull Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        try {
            FragmentManager fm = getChildFragmentManager();
            String fragmentName = fragment.getClass().getName();
            fm.beginTransaction().replace(containerId,fragment,fragmentName).commit();
            if (addToBackStack) stackSet.push(fragment);
            ((BaseActivitykt) activity).hideKeyboard();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
