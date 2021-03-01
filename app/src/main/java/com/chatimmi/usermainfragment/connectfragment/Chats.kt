package com.chatimmi.usermainfragment.connectfragment

import android.net.Uri
import java.io.Serializable



class Chats : Serializable {
    //Todo Socket chat used variable
    var name: String? = null
    var uid: String? = null
    var chatRoomId: String? = null
    var channelId: String? = null
    var title: String? = null
    var roomType: String? = null
    var messageId: String? = null
    var fileType = ""
    var imageUrl = ""
    var currentTime = ""
    var createdTime = ""
    var from = ""
    var unreadCount = 0
    var badgeCount = 0
    var isOnline = 0
    var lastSeenTimeStamp: Long? = null
    var isNotification = 0
    var muteNotification = 0
    var memberType = 0
    var member_type_reference_id = 0
    var member_tlastSeenTimeStampype_reference_id: String? = null
    var eventEndDate: String? = null

    //Todo firebase chat used variable
    var deleteby: String? = null
    var firebaseId: String? = null
    var firebaseToken: String? = null
    var authToken: String? = null
    var image = 0
    var message: String? = null
    var profilePic: String? = null
    var timestamp: Long? = null
    var lastMsg: String? = null
    var lastMsgTime: String? = null
    var isBlocked: String? = null
    var socketRoom: String? = null
    var lastMsgId: String? = null
    var lastSeenString: String? = null
    var banner_date = ""
    var isMsgReadTick = 0
    var imgURI: Uri? = null
    var type: String? = null
    var isGroup = false
    var groupUserCount = 0
    var userName: String? = null
    var message_type: String? = null
    var eventInfoDetails: EventInfoDetails? = null


    class EventInfoDetails : Serializable {
        var eventId: String? = null
        var ownerType: String? = null
        var eventName: String? = null
        var eventType: String? = null
        var eventImage: String? = null
        var eventOrganizerId: String? = null
        var eventOrganizerName: String? = null
        var eventOrganizerProfileImage: String? = null
        var compId: String? = null
        var eventMemId: String? = null
    }
}
