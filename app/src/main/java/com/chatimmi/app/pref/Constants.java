package com.chatimmi.app.pref;

public interface Constants {
    /**
     * Retrofit constants
     */
    Long CONNECTION_TIMEOUT = 5 * 60L;
    Long READ_TIMEOUT = 5 * 60L;


    /**
     * Keys below
     */
    String KEY_RESULT_EXTRA = "result_extra";
    String KEY_BUNDLE_PARAM = "extra_data";


    /**
     * Request code constants
     */
    int PICK_IMAGE_REQUEST = 1001;
    int CAPTURE_IMAGE_REQUEST = 1002;
    int STORAGE_PERMISSION = 1003;
    int LOCATION_PERMISSION = 1004;
    int PLACE_PICKER_REQUEST = 1005;
    int COUPON_FILTER_REQUEST = 1006;
    int COUNTRY_REQUEST = 1007;
}
