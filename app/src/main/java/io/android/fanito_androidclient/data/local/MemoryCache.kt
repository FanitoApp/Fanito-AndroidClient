package io.android.fanito_androidclient.data.local

import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.PortfolioResponse
import io.android.fanito_androidclient.data.model.api.response.UserPersonalResponse
import retrofit2.Response
import java.util.*


object MemoryCache {

    // The balance of tokens in the user's wallet
    private var userPortfolioMap = HashMap<String, List<PortfolioResponse>>()
    private var TokensMap = HashMap<String, GetTokensResponse>()
    fun userPortfolioExist(userId: String) = userPortfolioMap.containsKey(userId)
    fun userTokensExist() = TokensMap.containsKey("token")
    fun getUserPortfolio(userId: String) = userPortfolioMap[userId]
    fun getTokens() = TokensMap["token"]
    fun setUserPortfolio(userId: String, portfolio: List<PortfolioResponse>?) =
        portfolio?.let { userPortfolioMap[userId] = it }
    fun setTokens(Token: GetTokensResponse?) =
        Token?.let { TokensMap["token"] = it }

    // The personal information of the user
    private var userPersonal: UserPersonalResponse? = null
    fun getUserPersonal() = userPersonal
    fun setUserPersonal(userPersonal: UserPersonalResponse?) =
        userPersonal?.let { MemoryCache.userPersonal = it }

    fun clean() {
        userPortfolioMap.clear()
        userPersonal = null
    }

}