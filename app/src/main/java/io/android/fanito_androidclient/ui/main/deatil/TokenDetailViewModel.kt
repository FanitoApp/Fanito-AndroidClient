package io.android.fanito_androidclient.ui.main.deatil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.UserDetailsResponse
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject
@HiltViewModel
class TokenDetailViewModel @Inject constructor(dataManager: DataManager) :
    BaseViewModel<TokenDetailNavigator>(dataManager) {

    private val _token = MutableLiveData<GetTokensResponse.Token>()
    val token: LiveData<GetTokensResponse.Token> = _token

    private val _tokenIssuer = MutableLiveData<UserDetailsResponse>()
    val tokenIssuer: LiveData<UserDetailsResponse> = _tokenIssuer


    fun init(tokenJson: String, issuerJson: UserDetailsResponse) {
        val token = dataManager.jsonToObject(tokenJson, GetTokensResponse.Token::class.java)
        _tokenIssuer.postValue(issuerJson)
        token?.let { it ->
            _token.postValue(it)

        }

    }



}