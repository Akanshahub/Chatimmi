package com.chatimmi.retrofitnetwork;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chatimmi.base.BaseActivitykt;
import com.chatimmi.model.ErrorResponse;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Response;

public abstract class Callback<T> implements retrofit2.Callback<T> {
    private boolean showProgress = false;
    private Context context;
    /*private ProgressBarUtil progressBar;

    public Callback(Context context) {
        this.context = context;
        showProgress(true);
    }

    public Callback() {
        this.context = context;
        this.showProgress = showProgress;
        showProgress(showProgress);
    }*/

/*    private void showProgress(boolean showProgress) {

        if (showProgress) {
            if(context != null){
                progressBar = new ProgressBarUtil(context);
                progressBar.show();
            }

        }
    }*/
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
       // if (progressBar != null) progressBar.hide();

        if (response.code() == 200) {
            if (response.body() != null) {
                onSuccess(response.body());
            } else {
                onError(new Throwable("Request onSuccess with null response"));
            }
        }
        else if (response.code() == 400) {
            //ToDo: Need to handle this onError code and remove error code
            //onError(new Throwable("Handle logout error: " + response.toString()));
            if (response.errorBody() != null) {
                ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                if (context instanceof BaseActivitykt &&
                        "SESSION_EXPIRED".equals(errorResponse.getErrorType()) ||
                        "INVALID_TOKEN".equals(errorResponse.getErrorType()) ||
                        "USER_NOT_FOUND".equals(errorResponse.getErrorType())) {
                    Toast.makeText(context, "Session expired please login again", Toast.LENGTH_SHORT).show();
                       onSessionExpire(new Throwable(errorResponse.getMessage()));
//                    PrefHelper.instance().logout(context);
//                    navigateTo(RoleSelectionActivity.class, null, true);
                } else {
                    onError(new Throwable(errorResponse.getMessage()));
                }
            } else {
                onError(new Throwable("Request onSuccess with an unknown error: " + response.toString()));
            }


        }
        else {
            if (response.errorBody() != null) {
                onError(new Throwable("Request onSuccess with an error: " + response.errorBody().charStream()));
            } else {
                onError(new Throwable("Request onSuccess with an unknown error: " + response.toString()));
            }
        }

    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
       // if (progressBar != null) progressBar.hide();
        onError(t);
    }

    protected abstract void onSuccess(T response);
    public abstract void onError(Throwable t);
    public abstract void onSessionExpire(Throwable t);

}