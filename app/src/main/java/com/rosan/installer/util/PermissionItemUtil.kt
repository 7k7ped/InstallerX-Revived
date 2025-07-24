package com.rosan.installer.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import com.rosan.installer.R
import timber.log.Timber

/**
 * 获取权限标签的资源ID。
 * 如果权限在自定义列表中，则返回对应的资源ID，否则返回未知权限的资源ID。
 *
 * @param permission 权限字符串
 * @return 权限标签的资源ID
 */
@StringRes
private fun getPermissionLabelRes(permission: String): Int = when (permission) {
    "android.permission.ACCESS_BACKGROUND_LOCATION" -> R.string.permission_access_background_location
    "android.permission.ACCESS_COARSE_LOCATION" -> R.string.permission_access_coarse_location
    "android.permission.ACCESS_FINE_LOCATION" -> R.string.permission_access_fine_location
    "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" -> R.string.permission_access_location_extra_commands
    "android.permission.ACCESS_MEDIA_LOCATION" -> R.string.permission_access_media_location
    "android.permission.ACCESS_MOCK_LOCATION" -> R.string.permission_access_mock_location
    "android.permission.ACCESS_NETWORK_STATE" -> R.string.permission_access_network_state
    "android.permission.ACCESS_NOTIFICATION_POLICY" -> R.string.permission_access_notification_policy
    "android.permission.ACCESS_WIFI_STATE" -> R.string.permission_access_wifi_state
    "android.permission.ANSWER_PHONE_CALLS" -> R.string.permission_answer_phone_calls
    "android.permission.BLUETOOTH" -> R.string.permission_bluetooth
    "android.permission.BLUETOOTH_ADMIN" -> R.string.permission_bluetooth_admin
    "android.permission.BLUETOOTH_CONNECT" -> R.string.permission_bluetooth_connect
    "android.permission.BLUETOOTH_SCAN" -> R.string.permission_bluetooth_scan
    "android.permission.CALL_PHONE" -> R.string.permission_call_phone
    "android.permission.CAMERA" -> R.string.permission_camera
    "android.permission.FOREGROUND_SERVICE" -> R.string.permission_foreground_service
    "android.permission.GET_ACCOUNTS" -> R.string.permission_get_accounts
    "android.permission.INTERNET" -> R.string.permission_internet
    "android.permission.MANAGE_ACCOUNTS" -> R.string.permission_manage_accounts
    "android.permission.NFC" -> R.string.permission_nfc
    "android.permission.POST_NOTIFICATIONS" -> R.string.permission_post_notifications
    "android.permission.READ_CALENDAR" -> R.string.permission_read_calendar
    "android.permission.READ_CALL_LOG" -> R.string.permission_read_call_log
    "android.permission.READ_CONTACTS" -> R.string.permission_read_contacts
    "android.permission.READ_EXTERNAL_STORAGE" -> R.string.permission_read_external_storage
    "android.permission.READ_MEDIA_AUDIO" -> R.string.permission_read_media_audio
    "android.permission.READ_MEDIA_IMAGES" -> R.string.permission_read_media_images
    "android.permission.READ_MEDIA_VIDEO" -> R.string.permission_read_media_video
    "android.permission.READ_PHONE_NUMBERS" -> R.string.permission_read_phone_numbers
    "android.permission.READ_PHONE_STATE" -> R.string.permission_read_phone_state
    "android.permission.READ_SMS" -> R.string.permission_read_sms
    "android.permission.READ_SYNC_SETTINGS" -> R.string.permission_read_sync_settings
    "android.permission.RECEIVE_MMS" -> R.string.permission_receive_mms
    "android.permission.RECEIVE_SMS" -> R.string.permission_receive_sms
    "android.permission.RECORD_AUDIO" -> R.string.permission_record_audio
    "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" -> R.string.permission_request_ignore_battery_optimizations
    "android.permission.REQUEST_INSTALL_PACKAGES" -> R.string.permission_request_install_packages
    "android.permission.SCHEDULE_EXACT_ALARM" -> R.string.permission_schedule_exact_alarm
    "android.permission.SEND_SMS" -> R.string.permission_send_sms
    "android.permission.SYSTEM_ALERT_WINDOW" -> R.string.permission_system_alert_window
    "android.permission.USE_CREDENTIALS" -> R.string.permission_use_credentials
    "android.permission.VIBRATE" -> R.string.permission_vibrate
    "android.permission.WRITE_CALENDAR" -> R.string.permission_write_calendar
    "android.permission.WRITE_CALL_LOG" -> R.string.permission_write_call_log
    "android.permission.WRITE_CONTACTS" -> R.string.permission_write_contacts
    "android.permission.WRITE_EXTERNAL_STORAGE" -> R.string.permission_write_external_storage
    "android.permission.WRITE_SETTINGS" -> R.string.permission_write_settings
    "android.permission.WRITE_SYNC_SETTINGS" -> R.string.permission_write_sync_settings
    "com.rosan.dhizuku.permission.API" -> R.string.permission_dhizuku
    else -> R.string.permission_unknown
}

/**
 * 获取权限标签的最佳方法。
 * 优先从我们自定义的列表中查找，如果找不到，再向系统查询。
 *
 * @param context 上下文
 * @param permission 权限字符串
 * @return 最佳的权限标签字符串
 */
fun getBestPermissionLabel(context: Context, permission: String): String {
    val customResId = getPermissionLabelRes(permission)

    return if (customResId != R.string.permission_unknown)
        context.getString(customResId)
    else try {
        val pm = context.packageManager
        val permissionInfo = pm.getPermissionInfo(permission, 0)
        if (permission == permissionInfo.loadLabel(pm).toString())
            context.getString(R.string.permission_unknown)
        else permissionInfo.loadLabel(pm)
            .toString()
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e.printStackTrace().toString())
        // 如果系统中也没有这个权限，返回一个默认的“未知”标签
        context.getString(R.string.permission_unknown)
    }
}