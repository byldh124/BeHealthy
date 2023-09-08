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
    const val ITEMS = "ITEMS"
}

object ApiParam {
    const val VERSION_CODE = "versionCode"
    const val VERSION_NAME = "versionName"
    const val PACKAGE_NAME = "packageName"

    const val ID = "id"
    const val INDEX = "index"
    const val BOX_COLOR = "boxColor"
}

object RoomParam {
    const val PROFILE_TABLE_NAME = "BH_Profile"
    const val PROFILE_ID = "PROFILE_ID"
    const val PROFILE_NAME = "PROFILE_NAME"
    const val PROFILE_THUMB = "PROFILE_THUMB"
    const val PROFILE_TOKEN = "PROFILE_TOKEN"
    const val PROFILE_TYPE = "PROFILE_TYPE"
}

enum class UserType(val value: Int) {
    GUEST(0), KAKAO(1), GOOGLE(2)
}

enum class ItemType(val value: Int) {
    SMOKE(1), DRINK(2)
}

object TimeHelper {
    const val SECOND = 1000L
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR
}
