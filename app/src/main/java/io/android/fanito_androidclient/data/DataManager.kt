package io.android.fanito_androidclient.data

import io.android.fanito_androidclient.data.local.prefs.PreferencesHelper
import io.android.fanito_androidclient.data.remote.ApiHelper
import io.android.fanito_androidclient.data.resource.ResourceHelper
import retrofit2.Response

interface DataManager : PreferencesHelper, ApiHelper, ResourceHelper {

    fun objectToJson(`object`: Any): String

    fun <T> jsonToObject(jsonString: String, classType: Class<T>): T?

    /**
     *The text related to server response error
     *
     * @param response The response received from the server
     */
    fun responseErrorMessage(response: Response<*>): String

    /**
     *Server response error code
     *
     * @param response
    The response received from the server
     */
    fun responseErrorCode(response: Response<*>): Int?
}