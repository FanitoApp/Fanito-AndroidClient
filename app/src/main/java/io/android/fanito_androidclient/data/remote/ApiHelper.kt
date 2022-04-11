package io.android.fanito_androidclient.data.remote


import io.android.fanito_androidclient.data.model.api.response.*
import retrofit2.Response

interface ApiHelper {

    /**
     *Request for sending a one-time verification code
     *
     * @param phone The mobile number for sending the one-time verification code
     */
    suspend fun sendOtp(phone: String): Response<SendOtpResponse>

    /**
     *Confirm sending a one-time code
     *
     * @param phone The mobile number to which the one-time verification code has been sent
     * @param otp The one-time verification code that has been sent
     * @param sessionId The identifier associated with the verification of the one-time code
     */
    suspend fun verifyOtp(phone: String, otp: String, sessionId: String): Response<VerifyOtpResponse>

    /**
     *Logging into the user account
     *
     * @param otpSessionId The identifier associated with the verification of the one-time code for login purposes
     */
    suspend fun login(otpSessionId: String): Response<LoginResponse>

    /**
     *Get user information
     *
     * @param userId User identifier
     */
    suspend fun userDetails(userId: String): Response<UserDetailsResponse>

    /**
     *Get user's personal information
     *
     * @param userId User identifier
     */
    suspend fun userPersonal(userId: String): Response<UserPersonalResponse>

    /**
     *Get the list of user tokens
     *
     * @param userId User identifier
     */
    suspend fun userPortfolio(userId: String): Response<List<PortfolioResponse>>

    /**
     *Get a list of user transactions
     *
     * @param userId User identifier
     * @param limit The number of transactions requested
     */
    suspend fun getTxs(userId: String, limit: Int, offset: Int): Response<GetTxsResponse>

    /**
     *Get the list of available tokens (display on the main page)
     *
     * @param limit Number of tokens requested
     */
    suspend fun getTokens(limit: Int, offset: Int): Response<GetTokensResponse>

    /**
     *Get a list of all polls
     *
     * @param limit Number of tokens requested
     */
    suspend fun getPolls(
        limit: Int,
        offset: Int,
        tokenSymbol: String? = null
    ): Response<GetPollsResponse>

    /**
     *Get a list of survey options
     *
     * @param pollId Survey identifier
     */
    suspend fun pollOptions(pollId: String): Response<PollOptionsResponse>

    /**
     *Register to vote for a poll
     *
     * @param userId User identifier
     * @param pollId Survey identifier
     * @param optionId ID of the selected option
     * @param weight Weight (currently fixed at 100)
     */
    suspend fun registerVote(
        userId: String,
        pollId: String,
        optionId: Int,
        weight: Int
    ): Response<Any>

    /**
     *
    Checking the user's vote in a poll
     *
     * @param pollId Survey identifier
     */
    suspend fun checkUserVote(pollId: String): Response<CheckUserVoteResponse>

}