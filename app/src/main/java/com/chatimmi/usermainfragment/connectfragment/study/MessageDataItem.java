package com.chatimmi.usermainfragment.connectfragment.study;

public class MessageDataItem{
	private int isTickRead;
	private String updatedAt;
	private int groupId;
	private String createdOn;
	private String imageUrl;
	private int messageID;
	private int isImage;
	private int recieverId;
	private String message;
	private int senderId;

	public int getIsTickRead(){
		return isTickRead;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getGroupId(){
		return groupId;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public int getMessageID(){
		return messageID;
	}

	public int getIsImage(){
		return isImage;
	}

	public int getRecieverId(){
		return recieverId;
	}

	public String getMessage(){
		return message;
	}

	public int getSenderId(){
		return senderId;
	}
}
