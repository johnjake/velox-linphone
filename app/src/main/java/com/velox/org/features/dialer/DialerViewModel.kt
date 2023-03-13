package com.velox.org.features.dialer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class DialerViewModel : ViewModel() {
    private val padFlow: MutableSharedFlow<Int> = MutableSharedFlow()
    val padShared: SharedFlow<Int> = padFlow

    fun clickPadNumber(number: Int) {
        viewModelScope.launch {
            padFlow.emit(number)
        }
    }
}
