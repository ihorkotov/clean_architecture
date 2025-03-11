package com.moto.tablet.util

import androidx.annotation.StringRes
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Message(
    val id: Long,
    @StringRes val messageId: Int? = null,
    val message: String? = null,
    val isErrorMessage: Boolean = false
)

/**
 * Class responsible for managing Snackbar messages to show on the screen
 */

object SnackbarManager {

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages.asStateFlow()

    fun showMessage(@StringRes messageTextId: Int, isErrorMessage: Boolean = false) {
        _messages.update { currentMessages ->
            currentMessages + Message(
                id = UUID.randomUUID().mostSignificantBits,
                messageId = messageTextId,
                isErrorMessage = isErrorMessage
            )
        }
    }

    fun showMessage(message: String, isErrorMessage: Boolean = false) {
        _messages.update { currentMessages ->
            currentMessages + Message(
                id = UUID.randomUUID().mostSignificantBits,
                message = message,
                isErrorMessage = isErrorMessage
            )
        }
    }

    fun showErrorMessage(@StringRes messageTextId: Int) {
        showMessage(messageTextId, true)
    }

    fun showErrorMessage(message: String) {
        showMessage(message, true)
    }

    fun setMessageShown(messageId: Long) {
        _messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }
}
