package com.moondroid.behealthy.common

object ResponseCode {
    const val SUCCESS = 1000
    const val FAIL = 2000
    const val NOT_EXIST = 2001
    const val ALREADY_EXIST = 2002
    const val INVALID_VALUE = 2003
    const val INACTIVE = 2004
}

object IntentParam {
    const val ITEM_SIZE = "ITEM_SIZE"
}

object ApiParam {
    const val VERSION_CODE = "versionCode"
    const val VERSION_NAME = "versionName"
    const val PACKAGE_NAME = "packageName"

    const val ID = "id"
}

object RoomParam {
    const val PROFILE_TABLE_NAME = "BH_Profile"
    const val PROFILE_ID = "PROFILE_ID"
    const val PROFILE_NAME = "PROFILE_NAME"
    const val PROFILE_THUMB = "PROFILE_THUMB"
    const val PROFILE_TOKEN = "PROFILE_TOKEN"
    const val PROFILE_TYPE = "PROFILE_TYPE"
}

object UserType {
    const val GUEST = 0
    const val KAKAO = 1
    const val GOOGLE = 2
}

object ItemType {
    const val SMOKE = 1
    const val DRINK = 2
}

object TimeHelper {
    const val SECOND = 1000L
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR
}
