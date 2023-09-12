package com.moondroid.behealthy.legacy.view.ui.item.list

import androidx.lifecycle.viewModelScope
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onFail
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.GetSayingUseCase
import com.moondroid.behealthy.domain.usecase.item.GetItemsUseCase
import com.moondroid.behealthy.legacy.BHApp
import com.moondroid.behealthy.legacy.view.base.BaseViewModel
import com.moondroid.behealthy.legacy.utils.MutableEventFlow
import com.moondroid.behealthy.legacy.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val getSayingUseCase: GetSayingUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ItemListEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    init {
        getSaying()
    }

    private fun getSaying() {
        viewModelScope.launch {
            getSayingUseCase().collect { result ->
                result.onSuccess {
                    _eventFlow.emit(ItemListEvent.Saying(it))
                }.onFail {
                    debug("getSaying() - fail[$it]")
                }.onError {
                    it.logException()
                }
            }
        }
    }

    fun getItems() {
        debug("getItems()")
        viewModelScope.launch {
            getItemsUseCase(BHApp.profile.id).collect { result ->
                result.onSuccess {
                    debug("getItems() - onSuccess() $it")
                    _eventFlow.emit(ItemListEvent.UpdateItem(it))
                }.onFail {
                    debug("getItems() - fail[$it]")
                }.onError {
                    it.logException()
                }
            }
        }
    }

    sealed class ItemListEvent {
        data class UpdateItem(val list: List<Item>) : ItemListEvent()
        data class Saying(val urls: List<String>) : ItemListEvent()
    }
}