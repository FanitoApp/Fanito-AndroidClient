package io.android.fanito_androidclient.date.local.prefs

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String?
) : PreferencesHelper {

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun clearUserData() = mPrefs.edit().clear().apply()

    override var refreshToken: String?
        get() = mPrefs.getString(PREF_KEY_REFRESH_TOKEN, null)
        set(value) {
            mPrefs.edit().putString(PREF_KEY_REFRESH_TOKEN, value).apply()
        }




    override var accessToken: String?
        get()
        {
            mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)?.let{
                var jwt: JWT = JWT(it)
                var ExpireDate= jwt.expiresAt
                if(Date()?.before(ExpireDate))
                {

                }
            }

            return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)
        }
        set(value) {
            mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()
        }

    override var userId: String?
        get() = mPrefs.getString(PREF_KEY_USER_ID, null)
        set(value) {
            mPrefs.edit().putString(PREF_KEY_USER_ID, value).apply()
        }

    companion object {
        private const val PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
    }
}