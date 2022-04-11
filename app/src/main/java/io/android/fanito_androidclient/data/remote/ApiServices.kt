package io.android.fanito_androidclient.data.remote

import io.android.fanito_androidclient.data.model.api.request.LoginRequest
import io.android.fanito_androidclient.data.model.api.request.RegisterVoteRequest
import io.android.fanito_androidclient.data.model.api.request.SendOtpRequest
import io.android.fanito_androidclient.data.model.api.request.VerifyOtpRequest
import io.android.fanito_androidclient.data.model.api.response.*
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_LOGIN
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_POLLS
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_POLL_OPTIONS
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_POLL_REGISTER_VOTE
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_SEND_OTP
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_TOKENS
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_TXS
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_USER_DETAILS
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_USER_PERSONAL
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_USER_PORTFOLIO
import io.android.fanito_androidclient.data.remote.ApiEndPoint.ENDPOINT_VERIFY_OTP
import io.android.fanito_androidclient.utils.Retry
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @Retry
    @POST(ENDPOINT_SEND_OTP)
    suspend fun sendOtp(@Body body: SendOtpRequest): Response<SendOtpResponse>
    @Retry
    @POST(ENDPOINT_VERIFY_OTP)
    suspend fun verifyOtp(@Body body: VerifyOtpRequest): Response<VerifyOtpResponse>
    @Retry
    @POST(ENDPOINT_LOGIN)
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>
    @Retry
    @GET(ENDPOINT_USER_DETAILS)
    suspend fun userDetails(@Path("id") userId: String): Response<UserDetailsResponse>
    @Retry
    @GET(ENDPOINT_USER_PERSONAL)
    suspend fun userPersonal(@Path("id") userId: String): Response<UserPersonalResponse>
    @Retry
    @GET(ENDPOINT_USER_PORTFOLIO)
    suspend fun userPortfolio(@Path("id") userId: String): Response<List<PortfolioResponse>>
    @Retry
    @GET(ENDPOINT_TXS)
    suspend fun getTxs(
        @Query("userId") userId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<GetTxsResponse>
    @Retry
    @GET(ENDPOINT_TOKENS)
    suspend fun getTokens(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<GetTokensResponse>
    @Retry
    @GET(ENDPOINT_POLLS)
    suspend fun getPolls(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("tokenSymbol") tokenSymbol: String? = null,
    ): Response<GetPollsResponse>
    @Retry
    @GET(ENDPOINT_POLL_OPTIONS)
    suspend fun pollOptions(@Path("id") pollId: String): Response<PollOptionsResponse>
    @Retry
    @POST(ENDPOINT_POLL_REGISTER_VOTE)
    suspend fun registerVote(
        @Path("id") pollId: String,
        @Body body: RegisterVoteRequest
    ): Response<Any>
    @Retry
    @GET(ENDPOINT_POLL_REGISTER_VOTE)
    suspend fun checkUserVote(@Path("id") pollId: String): Response<CheckUserVoteResponse>

}