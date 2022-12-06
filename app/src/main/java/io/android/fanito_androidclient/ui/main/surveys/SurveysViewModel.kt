package io.android.fanito_androidclient.ui.main.surveys

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.ui.main.surveys.adapter.SurveysDataSource
import javax.inject.Inject

@HiltViewModel
class SurveysViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<SurveysNavigator>(dataManager) {

  val surveys = Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
    SurveysDataSource(dataManager)
  }).flow.cachedIn(viewModelScope)

}