package io.android.fanito_androidclient.date.local.prefs

interface PreferencesHelper {

    fun clearUserData()

    var refreshToken: String?
    var accessToken: String?
    var userId: String?
}