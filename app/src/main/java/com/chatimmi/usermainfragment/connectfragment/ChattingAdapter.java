package com.chatimmi.usermainfragment.connectfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import com.chatimmi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Anil on 13/6/18.
 **/

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int VIEW_TYPE_ME = 1;
    private int VIEW_TYPE_OTHER = 2;

    Context context;
    ArrayList<Chat.Data.MessageData> chatList;
    String myUid;
    //   GetDateStatus getDateStatus;
    boolean ishideName;
    public static final String DATE_FORMAT_12 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_13 = "hh:mm a";

    public ChattingAdapter(Context context, ArrayList<Chat.Data.MessageData> chatList,String myId) {
        this.context = context;
        this.chatList = chatList;
        this.myUid = myId;

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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView my_message, my_date_time_;
        RelativeLayout ly_my_image_view;
        ImageView iv_my_side_img, iv_msg_tick;
        TextView tv_days_status;
        ProgressBar my_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_message = itemView.findViewById(R.id.my_message);
            ly_my_image_view = itemView.findViewById(R.id.ly_my_image_view);

            my_date_time_ = itemView.findViewById(R.id.my_date_time_);
            tv_days_status = itemView.findViewById(R.id.tv_days_status);
            iv_msg_tick = itemView.findViewById(R.id.iv_msg_tick);
            my_progress = itemView.findViewById(R.id.my_progress);

        }

        void myBindData(final Chat.Data.MessageData chat, int tempPos) {
            ly_my_image_view.setVisibility(View.GONE);
            my_message.setVisibility(View.VISIBLE);
            my_message.setText(chat.getMessage() );
           /* if (chat.getMessage().equals("2")) {

                ly_my_image_view.setVisibility(View.VISIBLE);
                my_message.setVisibility(View.GONE);

                my_progress.setVisibility(View.VISIBLE);

                Glide.with(context).load(chat.imageUrl).listener(new RequestListener<Drawable>() {
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
                }).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(iv_my_side_img);


            } else {
                ly_my_image_view.setVisibility(View.GONE);
                my_message.setVisibility(View.VISIBLE);
                my_message.setText(chat.message);*/
            }

    /*        SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
            try {
                // String date = sd.format(new Date((Long) chat.timestamp));
                my_date_time_.setText(formatDateFromDateString(DATE_FORMAT_12, DATE_FORMAT_13, chat.createdTime));
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());

            }*/

            /*if (chat.isMsgReadTick == 1) {
                iv_msg_tick.setImageResource(R.drawable.ico_msg_received);
            } else*/
/*            if (chat.isMsgReadTick == 2) {
                iv_msg_tick.setImageResource(R.drawable.ico_msg_read);
            } else {
                iv_msg_tick.setImageResource(R.drawable.ico_msg_sent);
            }

            iv_my_side_img.setOnClickListener(view -> full_screen_photo_dialog(chat.imageUrl));
            getDateStatus.currentDateStatus(chat.timestamp);


            if (!chat.banner_date.equals(chatList.get(tempPos).banner_date)) {
                tv_days_status.setText(chat.banner_date);
                tv_days_status.setVisibility(View.VISIBLE);
            } else {
                tv_days_status.setVisibility(View.GONE);
            }

            if (!ishideName) {
                iv_msg_tick.setVisibility(View.GONE);
            }*/

        }


/*        public void full_screen_photo_dialog(String image_url) {
            final Dialog openDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            openDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            openDialog.setContentView(R.layout.full_image_view_dialog);
            ImageView iv_back = openDialog.findViewById(R.id.iv_back);
            iv_back.setOnClickListener(view -> openDialog.dismiss());

            PhotoView photoView = openDialog.findViewById(R.id.photo_view);
            if (!image_url.equals("")) {
                Glide.with(context).load(image_url).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(photoView);
            }chatList
            openDialog.show();

        }*/


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


    /**
     * @param time        in milliseconds (Timestamp)
     * @param mDateFormat SimpleDateFormat
     * @return
     */
    public static String getDateTimeFromTimeStamp(Long time, String mDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateTime = new Date(time);
        return dateFormat.format(dateTime);
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView other_message, other_date_time_;
        LinearLayout ly_other_image_view, ly_msg_view;
        ImageView iv_other_side_img;
        TextView tv_days_status, other_name, other_name_;
        ProgressBar other_progress;

        public OtherViewHolder(View itemView) {
            super(itemView);
            other_message = itemView.findViewById(R.id.other_message);
            ly_other_image_view = itemView.findViewById(R.id.ly_other_image_view);
            ly_msg_view = itemView.findViewById(R.id.ly_msg_view);


            tv_days_status = itemView.findViewById(R.id.tv_days_status);
            other_progress = itemView.findViewById(R.id.other_progress);

            other_name_ = itemView.findViewById(R.id.other_name_);
        }

        public void otherBindData(final Chat.Data.MessageData chat, int tempPos) {
            ly_other_image_view.setVisibility(View.GONE);
            other_message.setVisibility(View.VISIBLE);
            ly_msg_view.setVisibility(View.VISIBLE);
            other_message.setText(chat.getMessage());
            //  if (chat.image == 1) {
/*            if (chat.message_type.equals("2")) {
                ly_other_image_view.setVisibility(View.VISIBLE);
                other_message.setVisibility(View.GONE);
                ly_msg_view.setVisibility(View.GONE);

                other_progress.setVisibility(View.VISIBLE);
                Glide.with(context).load(chat.imageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        other_progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        other_progress.setVisibility(View.GONE);
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.placeholder_chat_image)).into(iv_other_side_img);

            } else {
                ly_other_image_view.setVisibility(View.GONE);
                other_message.setVisibility(View.VISIBLE);
                ly_msg_view.setVisibility(View.VISIBLE);
                other_message.setText(chat.message);
            }

            SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
            try {
                //String date = sd.format(new Date((Long) chat.timestamp));
                //other_date_time_.setText(date);
                other_date_time_.setText(formatDateFromDateString(DATE_FORMAT_12 , DATE_FORMAT_13,chat.createdTime));


            } catch (Exception ignored) {
                Log.e("Exception",ignored.getMessage());
            }


            iv_other_side_img.setOnClickListener(view -> full_screen_photo_dialog(chat.imageUrl));

            getDateStatus.currentDateStatus(chat.timestamp);

            if (!chat.banner_date.equals(chatList.get(tempPos).banner_date)) {
                String sDaysStatus = getLocalDayStatus(chat.banner_date);
                tv_days_status.setText(sDaysStatus);
                tv_days_status.setVisibility(View.VISIBLE);
            } else {
                tv_days_status.setVisibility(View.GONE);
            }

            if(ishideName){
                other_name.setVisibility(View.GONE);
                other_name_.setVisibility(View.GONE);
            }else {
                other_name.setVisibility(View.VISIBLE);
                other_name_.setVisibility(View.VISIBLE);
                other_name.setText(chat.name+"");
                other_name_.setText(chat.name+"");
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
        }*/
        }



  /*      private String getLocalDayStatus(String currentDay) {
            if (currentDay.equalsIgnoreCase("Today")) {
                return context.getString(R.string.today);
            } else if (currentDay.equalsIgnoreCase("Yesterday")) {
                return context.getString(R.string.yesterday);
            } else if (currentDay.contains("Today at")) {
                return currentDay.replace("Today at", context.getString(R.string.today_at));
            } else if (currentDay.contains("Yesterday at")) {
                return currentDay.replace("Yesterday at", context.getString(R.string.yesterday_at));
            } else if (currentDay.contains("at")) {
                return currentDay.replace("at", context.getString(R.string.at));
            } else {
                return currentDay;
            }
        }*/
    }

    public void refreshList(ArrayList<Chat.Data.MessageData> list){
        if(chatList.size()>0) chatList.clear();
        this.chatList = list;
        notifyDataSetChanged();
    }
}
