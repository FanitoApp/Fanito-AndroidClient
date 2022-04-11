package io.android.fanito_androidclient.data.local.prefs

interface PreferencesHelper {

    fun clearUserData()

    var refreshToken: String?
    var accessToken: String?
    var userId: String?
}