package com.chatimmi.usermainfragment.connectfragment;

import android.net.Uri;

import java.io.Serializable;

public class Chats implements Serializable {
    //Todo Socket chat used variable
    public String name;
    public String  uid;
    public String chatRoomId;
    public String channelId;
    public String title;
    public String roomType;
    public String messageId;
    public String fileType="";
    public String imageUrl="";
    public String currentTime="";
    public String createdTime="";
    public String from="";
    public int unreadCount;
    public int badgeCount;
    public int isOnline;
    public Long lastSeenTimeStamp;
    public int isNotification;
    public int muteNotification;
    public int memberType;
    public int member_type_reference_id;
    public String member_tlastSeenTimeStampype_reference_id;
    public String eventEndDate;


    //Todo firebase chat used variable
    public String deleteby;
    public String firebaseId;
    public String firebaseToken;
    public String authToken;
    public int   image;
    public String message;
    public String profilePic;
    public Long timestamp;
    public String lastMsg;
    public String lastMsgTime;
    public String isBlocked;
    public String socketRoom;
    public String lastMsgId;
    public String lastSeenString;

    public void Chat() {
    }

    public String banner_date="";
    public int isMsgReadTick;
    public Uri imgURI;

    public String type;
    public boolean isGroup=false;
    public int groupUserCount=0;
    public String userName;
    public String message_type;


    public EventInfoDetails eventInfoDetails;


    public static class EventInfoDetails implements Serializable{
        public EventInfoDetails() {
        }

        public String eventId;
        public String ownerType;
        public String eventName;
        public String eventType;
        public String eventImage;
        public String eventOrganizerId;
        public String eventOrganizerName;
        public String eventOrganizerProfileImage;
        public String compId;
        public String eventMemId;

    }
}
