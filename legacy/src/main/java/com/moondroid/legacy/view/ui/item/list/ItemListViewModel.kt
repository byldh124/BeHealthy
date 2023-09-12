package com.moondroid.legacy.view.ui.item.list

import androidx.lifecycle.viewModelScope
import com.moondroid.legacy.common.Extensions.debug
import com.moondroid.legacy.common.Extensions.logException
import com.moondroid.legacy.domain.model.Item
import com.moondroid.legacy.domain.model.status.onError
import com.moondroid.legacy.domain.model.status.onFail
import com.moondroid.legacy.domain.model.status.onSuccess
import com.moondroid.legacy.domain.usecase.application.GetSayingUseCase
import com.moondroid.legacy.domain.usecase.item.GetItemsUseCase
import com.moondroid.legacy.BHApp
import com.moondroid.legacy.utils.MutableEventFlow
import com.moondroid.legacy.utils.asEventFlow
import com.moondroid.legacy.view.base.BaseViewModel
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