package com.chatimmi.usermainfragment.connectfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chatimmi.R;
import com.chatimmi.helper.GetDateStatus;
import com.chatimmi.usermainfragment.connectfragment.chat.ChatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import uk.co.senab.photoview.PhotoView;



public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final String DATE_FORMAT_12 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_13 = "hh:mm a";
    Context context;
    ArrayList<Chat.Data.MessageData> chatList;
    String myUid;
    ChatActivity chatActivity;
    boolean ishideName;
    GetDateStatus getDateStatus;
    private int VIEW_TYPE_ME = 1;
    private int VIEW_TYPE_OTHER = 2;

    public ChattingAdapter(Context context, ArrayList<Chat.Data.MessageData> chatList, String myId, GetDateStatus getDateStatus, boolean ishideName, ChatActivity chatActivity) {
        this.context = context;
        this.chatList = chatList;
        this.myUid = myId;
        this.getDateStatus = getDateStatus;
        this.ishideName = ishideName;
        this.chatActivity = chatActivity;

        // this.getDateStatus = getDateStatus;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        if (viewType == VIEW_TYPE_ME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right_side_view, parent, false);
            holder = new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_OTHER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left_side_view, parent, false);
            holder = new OtherViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat.Data.MessageData chat = chatList.get(position);
        Log.d("fnkanfkla", "onBindViewHolder: "+chatList.size());

        int pos = position - 1;
        int tempPos = (pos == -1) ? pos + 1 : pos;

        if (holder instanceof MyViewHolder) {
            Log.d("fnkanfkla", "MyViewHolder: ");
            ((MyViewHolder) holder).myBindData(chat, tempPos);
        } else if (holder instanceof OtherViewHolder) {
            Log.d("fnkanfkla", "MyViewHolder: ");
            ((OtherViewHolder) holder).otherBindData(chat, tempPos);
        }else {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(chatList.get(position).getSenderId(), myUid)) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setMyId(String myUid) {
        this.myUid = myUid;
    }

    public void isNameShow(boolean b) {
        ishideName = b;
    }

    public void full_screen_photo_dialog(String image_url) {
        final Dialog openDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        openDialog.setContentView(R.layout.full_image_view_dialog);
        ImageView iv_back = openDialog.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> openDialog.dismiss());

        PhotoView photoView = openDialog.findViewById(R.id.photo_view);
        if (!image_url.equals("")) {
            Glide.with(context).load(image_url).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(photoView);
        }
        openDialog.show();

    }

    public String formatDateFromDateString(String inputDateFormat, String outputDateFormat,
                                           String inputDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = formatter.parse(inputDate);

        SimpleDateFormat sd = new SimpleDateFormat(outputDateFormat, Locale.getDefault());
        sd.setTimeZone(TimeZone.getDefault());
        String time = sd.format(value);
        return time;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView my_message, my_date_time_;
        RelativeLayout ly_my_image_view;
        ImageView iv_My_image_chat, iv_msg_tick;
        TextView tv_days_status;
        ProgressBar my_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_message = itemView.findViewById(R.id.my_message);
            ly_my_image_view = itemView.findViewById(R.id.ly_my_image_view);
            iv_My_image_chat = itemView.findViewById(R.id.iv_My_image_chat);

            my_date_time_ = itemView.findViewById(R.id.my_date_time_);
            tv_days_status = itemView.findViewById(R.id.tv_days_status);
            iv_msg_tick = itemView.findViewById(R.id.iv_msg_tick);
            my_progress = itemView.findViewById(R.id.my_progress);

        }

        void myBindData(final Chat.Data.MessageData chat, int tempPos) {
          //  tv_days_status.setVisibility(View.GONE);
            ly_my_image_view.setVisibility(View.GONE);
            my_message.setVisibility(View.VISIBLE);
            my_message.setText(chat.getMessage());
            try {

//                String strDate = chat.getCreatedOn();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date objDate = dateFormat.parse(strDate);
//                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//                String finalDate = dateFormat2.format(objDate);
//                Log.d("DateFormat:", "Final Date:"+finalDate);
//                if (mPreviousTime.equals(finalDate)) {
//                    Log.d("sdjisjdiosjd", "myBindData: " +finalDate + "----------------" + mPreviousTime);
//                    tv_days_status.setVisibility(View.GONE);
//
//                } else {
//                    mPreviousTime = finalDate;
//                    chatActivity.getYesterdayDate(chat.getCreatedOn(),tv_days_status);
//                    tv_days_status.setVisibility(View.VISIBLE);
//                }

                tv_days_status.setText(chat.getDayName());
                if(chat.getShouldVisibleShowDateView()){
                    tv_days_status.setVisibility(View.VISIBLE);
                }else {
                    tv_days_status.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // String time = chatList.get(tempPos).getCreatedOn();




            SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
            try {
                // String date = sd.format(new Date((Long) chat.timestamp));
                my_date_time_.setText(formatDateFromDateString(DATE_FORMAT_12, DATE_FORMAT_13, chat.getCreatedOn()));
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());

            }
            if (chat.isImage() == 1) {
                ly_my_image_view.setVisibility(View.VISIBLE);
                my_message.setVisibility(View.GONE);
                Glide.with(context).load(chat.getMessage()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        my_progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        my_progress.setVisibility(View.GONE);
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(iv_My_image_chat);

            } else {
                ly_my_image_view.setVisibility(View.GONE);
                my_message.setVisibility(View.VISIBLE);
                my_message.setText(chat.getMessage());
            }

            Log.d("djikwjdkwjd", "isTickRead " + chat.getIsread());
            if (1 == chat.getIsread()) {
                iv_msg_tick.setImageResource(R.drawable.ic_active_tick_ico);
            } else {
                iv_msg_tick.setImageResource(R.drawable.ic_inactive_tick_ico);
            }
            iv_My_image_chat.setOnClickListener(view -> full_screen_photo_dialog(chat.getMessage()));


        }


    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView other_message, other_date_time_;
        LinearLayout ly_other_image_view, ly_msg_view;
        ImageView iv_other_side_img, iv_msg_tick;
        TextView my_message, my_date_time_;
        TextView tv_days_statuss, other_name, other_name_;
        ProgressBar other_progress;

        public OtherViewHolder(View itemView) {
            super(itemView);
            other_message = itemView.findViewById(R.id.other_message);
            ly_other_image_view = itemView.findViewById(R.id.ly_other_image_view);
            iv_other_side_img = itemView.findViewById(R.id.iv_Other_image_chat);
            ly_msg_view = itemView.findViewById(R.id.ly_msg_view);
            my_message = itemView.findViewById(R.id.my_date_time_);
            other_date_time_ = itemView.findViewById(R.id.other_date_time_);
            tv_days_statuss = itemView.findViewById(R.id.tv_days_statusaa);
            iv_msg_tick = itemView.findViewById(R.id.iv_msg_tick);
            // other_progress = itemView.findViewById(R.id.other_progress);

            //  other_name_ = itemView.findViewById(R.id.other_name_);
        }

        public void otherBindData(final Chat.Data.MessageData chat, int tempPos) {
           // tv_days_statuss.setVisibility(View.GONE);
            try {

//                String strDate = chat.getCreatedOn();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date objDate = dateFormat.parse(strDate);
//                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//                String finalDate = dateFormat2.format(objDate);
//                Log.d("DateFormat:", "Final Date:"+finalDate);
//                if (mPreviousTime.equals(finalDate)) {
//                    Log.d("sdjisjdiosjd", "myBindData: " +finalDate + "----------------" + mPreviousTime);
//                    tv_days_statuss.setVisibility(View.GONE);
//
//                } else {
//                    mPreviousTime = finalDate;
//                    chatActivity.getYesterdayDate(chat.getCreatedOn(),tv_days_statuss);
//                    tv_days_statuss.setVisibility(View.VISIBLE);
//                }
                ly_other_image_view.setOnClickListener(view -> full_screen_photo_dialog(chat.getMessage()));
                tv_days_statuss.setText(chat.getDayName());
                if(chat.getShouldVisibleShowDateView()){
                    tv_days_statuss.setVisibility(View.VISIBLE);
                }else {
                    tv_days_statuss.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (chat.isImage() == 1) {

                ly_other_image_view.setVisibility(View.VISIBLE);
                iv_other_side_img.setVisibility(View.VISIBLE);
                other_message.setVisibility(View.GONE);
                ly_msg_view.setVisibility(View.VISIBLE);

                Glide.with(context).load(chat.getMessage()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.user_placeholder_img)).into(iv_other_side_img);
            } else {
                ly_other_image_view.setVisibility(View.GONE);
                other_message.setVisibility(View.VISIBLE);
                ly_msg_view.setVisibility(View.VISIBLE);
                other_message.setText(chat.getMessage());
                Log.d("aiousu", "otherBindData: "+chat.getMessage());
            }


            SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
            try {
                other_date_time_.setText(formatDateFromDateString(DATE_FORMAT_12, DATE_FORMAT_13, chat.getCreatedOn()));


            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }


        }

        public void full_screen_photo_dialog(String image_url) {
            final Dialog openDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            openDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            openDialog.setContentView(R.layout.full_image_view_dialog);
            ImageView iv_back = openDialog.findViewById(R.id.iv_back);
            iv_back.setOnClickListener(view -> openDialog.dismiss());

            PhotoView photoView = openDialog.findViewById(R.id.photo_view);
            if (!image_url.equals("")) {
                Glide.with(context).load(image_url).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(photoView);

            }
            openDialog.show();

        }

    }
}
