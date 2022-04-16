package io.android.fanito_androidclient.data


import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.gson.Gson
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.local.MemoryCache
import io.android.fanito_androidclient.data.local.prefs.PreferencesHelper
import io.android.fanito_androidclient.data.model.api.response.ApiResponseError
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.PortfolioResponse
import io.android.fanito_androidclient.data.model.api.response.UserPersonalResponse
import io.android.fanito_androidclient.data.remote.ApiHelper
import io.android.fanito_androidclient.data.resource.ResourceHelper
import io.android.fanito_androidclient.utils.AppConstants
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val mContext: Context,
    private val preferencesHelper: PreferencesHelper,
    private val apiHelper: ApiHelper,
    private val gson: Gson,
    private val resourceHelper: ResourceHelper
) : DataManager {

    override fun objectToJson(`object`: Any):String {
        return try {
            gson.toJson(`object`)
        } catch (e: Exception) {
            "null"
        }
    }
    override fun <T> jsonToObject(jsonString: String, classType: Class<T>): T? {
        return try {
            gson.fromJson(jsonString, classType)
        } catch (e: Exception) {
            null
        }
    }

    override fun responseErrorMessage(response: Response<*>): String {
        return when (responseErrorCode(response)) {
            AppConstants.ERROR_CODE_REACH_LIMITATION -> getString(R.string.operation_limit_exceeded)
            AppConstants.ERROR_CODE_INVALID_OTP -> getString(R.string.invalid_otp)
            else -> getString(R.string.operation_failed)
        }
    }

    override fun responseErrorCode(response: Response<*>): Int? {
        response.errorBody()?.let {
            val error = jsonToObject(it.string(), ApiResponseError::class.java)
            return error?.code
        }
        return null
    }

    override fun clearUserData() {
        MemoryCache.clean()
        preferencesHelper.clearUserData()
    }

    override var refreshToken: String?
        get() = preferencesHelper.refreshToken
        set(value) {
            preferencesHelper.refreshToken = value
        }

    override var accessToken: String?
        get() = preferencesHelper.accessToken
        set(value) {
            preferencesHelper.accessToken = value
        }

    override var userId: String?
        get() = preferencesHelper.userId
        set(value) {
            preferencesHelper.userId = value
        }

    override fun getString(@StringRes resourceId: Int) = resourceHelper.getString(resourceId)

    override fun getDrawable(@DrawableRes resourceId: Int) = resourceHelper.getDrawable(resourceId)

    override suspend fun sendOtp(phone: String) = apiHelper.sendOtp(phone)

    override suspend fun verifyOtp(
        phone: String,
        otp: String,
        sessionId: String
    ) = apiHelper.verifyOtp(phone, otp, sessionId)

    override suspend fun login(otpSessionId: String) = apiHelper.login(otpSessionId)

    override suspend fun userDetails(userId: String) = apiHelper.userDetails(userId)

    override suspend fun userPersonal(userId: String): Response<UserPersonalResponse> {
        return if (MemoryCache.getUserPersonal() != null)
            Response.success(200, MemoryCache.getUserPersonal())
        else {
            val result = apiHelper.userPersonal(userId)
            if (result.isSuccessful) MemoryCache.setUserPersonal(result.body())
            result
        }
    }

    override suspend fun userPortfolio(userId: String): Response<List<PortfolioResponse>> {
//    return if (MemoryCache.userPortfolioExist(userId))
//      Response.success(200, MemoryCache.getUserPortfolio(userId))
//    else {
        val result = apiHelper.userPortfolio(userId)
//      if (result.isSuccessful) MemoryCache.setUserPortfolio(userId, result.body())
        return result
//    }
    }
    override suspend fun getTxs(userId: String, limit: Int, offset: Int) =
        apiHelper.getTxs(userId, limit, offset)

    override suspend fun getTokens(limit: Int, offset: Int)
            : Response<GetTokensResponse> {
        return if (MemoryCache.userTokensExist())
            Response.success(200, MemoryCache.getTokens())
        else {
            val result = apiHelper.getTokens(limit, offset)
            if (result.isSuccessful) MemoryCache.setTokens(result.body())
            return result
//    }
        }

//  = apiHelper.getTokens(limit, offset)
    }
    override suspend fun getPolls(
        limit: Int, offset: Int,
        tokenSymbol: String?
    ) = apiHelper.getPolls(limit, offset, tokenSymbol)

    override suspend fun pollOptions(pollId: String) = apiHelper.pollOptions(pollId)

    override suspend fun registerVote(
        userId: String,
        pollId: String,
        optionId: Int,
        weight: Int
    ) = apiHelper.registerVote(userId, pollId, optionId, weight)

    override suspend fun checkUserVote(pollId: String) = apiHelper.checkUserVote(pollId)

    companion object {
        private const val TAG = "AppDataManager"
    }
}