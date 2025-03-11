package com.moto.tablet.ui.component

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.moto.tablet.util.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MotoScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarState = rememberSnackBarHostState()
    Scaffold(
        snackbarHost = {
            MotoSnackBarHost(snackbarState)
        },
        content = content
    )
}

@Composable
fun MotoSnackBarHost(state: MotoSnackBarHostState) {
    SnackbarHost(
        hostState = state.snackbarHostState,
    ) {
        MotoSnackBar(snackBarData = it, isShowError = state.isErrorMessage)
    }
}

@Composable
fun rememberSnackBarHostState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MotoSnackBarHostState = remember(snackbarHostState, snackbarManager, resources, coroutineScope) {
    MotoSnackBarHostState(snackbarHostState, snackbarManager, resources, coroutineScope)
}

@Stable
class MotoSnackBarHostState(
    val snackbarHostState: SnackbarHostState,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    var isErrorMessage: Boolean = false

    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val message = currentMessages[0]
                    val text: String? = when {
                        message.messageId != null -> resources.getText(message.messageId).toString()
                        message.message != null -> message.message
                        else -> null
                    }
                    isErrorMessage = message.isErrorMessage
                    // Notify the SnackbarManager so it can remove the current message from the list
                    snackbarManager.setMessageShown(message.id)
                    // Display the snackbar on the screen. `showSnackbar` is a function
                    // that suspends until the snackbar disappears from the screen
                    snackbarHostState.showSnackbar(text ?: "")
                }
            }
        }
    }
}

/**
 * A composable function that returns the [Resources]. It will be recomposed when `Configuration`
 * gets updated.
 */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
