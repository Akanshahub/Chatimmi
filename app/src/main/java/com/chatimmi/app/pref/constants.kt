package com.chatimmi.app.pref



interface constants {
    companion object {
        /**
         * Retrofit constants
         */
        const val CONNECTION_TIMEOUT = 5 * 60L
        const val READ_TIMEOUT = 5 * 60L

        /**
         * Keys below
         */
        const val KEY_RESULT_EXTRA = "result_extra"
        const val KEY_BUNDLE_PARAM = "extra_data"

        /**
         * Request code constants
         */
        const val PICK_IMAGE_REQUEST = 1001
        const val CAPTURE_IMAGE_REQUEST = 1002
        const val STORAGE_PERMISSION = 1003
        const val LOCATION_PERMISSION = 1004
        const val PLACE_PICKER_REQUEST = 1005
        const val COUPON_FILTER_REQUEST = 1006
        const val COUNTRY_REQUEST = 1007
    }
}