package io.android.fanito_androidclient.ui.main.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.NewsResponse
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(dataManager: DataManager) :
    BaseViewModel<NewsNavigator>(dataManager) {
    private val _new = MutableLiveData<NewsResponse>()
    val new :LiveData<NewsResponse> = _new

    fun init(tokenNews :String )
    {
        val token = dataManager.jsonToObject(tokenNews, NewsResponse::class.java)
        token?.let {
            it.drawable= it.drawbleId?.let { it1 -> dataManager.getDrawable(it1) }
        }

        token?.let {
            _new.postValue(it)
        }
    }
}