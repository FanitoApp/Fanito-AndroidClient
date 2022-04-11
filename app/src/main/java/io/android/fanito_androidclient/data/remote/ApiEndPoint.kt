package io.android.fanito_androidclient.data.remote

object ApiEndPoint {

    const val ENDPOINT_SEND_OTP: String = "/v1/auth/otp"
    const val ENDPOINT_VERIFY_OTP: String = "/v1/auth/otp/verify"
    const val ENDPOINT_LOGIN: String = "/v1/auth/login"
    const val ENDPOINT_USER_DETAILS: String = "/v1/users/{id}"
    const val ENDPOINT_USER_PERSONAL: String = "/v1/users/{id}/personal"
    const val ENDPOINT_USER_PORTFOLIO: String = "/v1/users/{id}/portfolio"
    const val ENDPOINT_TXS: String = "/v1/txs"
    const val ENDPOINT_TOKENS: String = "/v1/tokens"
    const val ENDPOINT_POLLS: String = "/v1/polls"
    const val ENDPOINT_POLL_OPTIONS: String = "/v1/polls/{id}/options"
    const val ENDPOINT_POLL_REGISTER_VOTE: String = "/v1/polls/{id}/votes"
}