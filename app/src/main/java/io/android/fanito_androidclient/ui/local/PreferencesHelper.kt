package io.android.fanito_androidclient.ui.local

interface PreferencesHelper {

    fun clearUserData()

    var refreshToken: String?
    var accessToken: String?
    var userId: String?
}