package io.android.fanito_androidclient.data.remote

import io.android.fanito_androidclient.data.model.api.request.LoginRequest
import io.android.fanito_androidclient.data.model.api.request.RegisterVoteRequest
import io.android.fanito_androidclient.data.model.api.request.SendOtpRequest
import io.android.fanito_androidclient.data.model.api.request.VerifyOtpRequest
import io.android.fanito_androidclient.data.model.api.response.GetTxsResponse
import io.android.fanito_androidclient.data.model.api.response.LoginResponse
import io.android.fanito_androidclient.data.model.api.response.SendOtpResponse
import io.android.fanito_androidclient.data.model.api.response.VerifyOtpResponse
import io.android.fanito_androidclient.utils.CommonUtils
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    private val apiServices: ApiServices
) : ApiHelper {

    override suspend fun sendOtp(phone: String): Response<SendOtpResponse> {
        val body = SendOtpRequest(phone)
        return apiServices.sendOtp(body)
    }

    override suspend fun verifyOtp(
        phone: String,
        otp: String,
        sessionId: String
    ): Response<VerifyOtpResponse> {
        val body = VerifyOtpRequest(sessionId, phone, otp)
        return apiServices.verifyOtp(body)
    }

    override suspend fun login(otpSessionId: String): Response<LoginResponse> {
        val body = LoginRequest(otpSessionId)
        return apiServices.login(body)
    }

    override suspend fun userDetails(userId: String) = apiServices.userDetails(userId)

    override suspend fun userPersonal(userId: String) = apiServices.userPersonal(userId)

    override suspend fun userPortfolio(userId: String) = apiServices.userPortfolio(userId)

    override suspend fun getTxs(userId: String, limit: Int, offset: Int): Response<GetTxsResponse> {
        // val pagination = mapOf(limit to "limit", offset to "offset")
        // return apiServices.getTxs(userId, pagination.toString())
        return apiServices.getTxs(userId, limit, offset)
    }

    override suspend fun getTokens(limit: Int, offset: Int) = apiServices.getTokens(limit, offset)

    override suspend fun getPolls(limit: Int, offset: Int, tokenSymbol: String?) =
        apiServices.getPolls(limit, offset, tokenSymbol)

    override suspend fun pollOptions(pollId: String) = apiServices.pollOptions(pollId)

    override suspend fun registerVote(
        userId: String,
        pollId: String,
        optionId: Int,
        weight: Int
    ): Response<Any> {
        val dateString = "${CommonUtils.dateToString(Date())}.100Z"
        val body = RegisterVoteRequest(userId, pollId, optionId, weight, dateString)
        return apiServices.registerVote(pollId, body)
    }

    override suspend fun checkUserVote(pollId: String) = apiServices.checkUserVote(pollId)
}