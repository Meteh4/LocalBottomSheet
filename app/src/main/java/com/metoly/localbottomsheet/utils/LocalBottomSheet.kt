package com.metoly.localbottomsheet.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import com.metoly.localbottomsheet.model.BottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * A ViewModel responsible for managing the state of a locally-scoped bottom sheet.
 *
 * This ViewModel handles the visibility of the bottom sheet and the content
 * displayed within it. It utilizes mutable state to ensure that UI components
 * observing these properties are updated automatically.
 */
class LocalBottomSheetViewModel : ViewModel() {
    var isVisible by mutableStateOf(false)

    var screen: BottomSheet<*>? by mutableStateOf(null)

    fun updateVisibility(visible: Boolean) {
        isVisible = visible
    }

    fun updateScreen(screen: BottomSheet<*>?) {
        this.screen = screen
    }
}

/**
 * Provides a [LocalBottomSheetManager] to its composable children.
 * This function should be placed at a high level in your composable hierarchy to make the bottom sheet functionality
 * accessible throughout your application.
 *
 * It initializes the necessary components for managing the bottom sheet, including:
 *  - A [LocalBottomSheetViewModel] using Koin dependency injection.
 *  - A [ModalBottomSheetState] to control the sheet's state.
 *  - A [CoroutineScope] for asynchronous operations.
 *  - A [LocalBottomSheetManager] instance that wraps these components and provides convenient methods
 *    for showing and hiding the bottom sheet.
 *
 * When the viewModel's `isVisible` property is true, it displays a [ModalBottomSheet] with the content provided
 * by the current bottom sheet screen.
 *
 * @param content The composable content that will have access to the [LocalBottomSheetManager].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalBottomSheetProvider(content: @Composable () -> Unit) {
    val viewModel: LocalBottomSheetViewModel = koinViewModel()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val manager = remember {
        LocalBottomSheetManager(viewModel, sheetState, scope)
    }

    CompositionLocalProvider(LocalBottomSheet provides manager) {
        content()

        if (viewModel.isVisible) {
            ModalBottomSheet(
                properties = ModalBottomSheetProperties(
                    shouldDismissOnBackPress = true,
                ),
                onDismissRequest = { manager.hide() },
                sheetState = sheetState
            ) {
                viewModel.screen?.let { screen ->
                    DisposableEffect(Unit) {
                        onDispose {
                            screen.closeScope()
                        }
                    }
                    screen.SheetContent()
                }
            }
        }
    }
}

val LocalBottomSheet = staticCompositionLocalOf<LocalBottomSheetManager> {
    error("LocalBottomSheet not initialized.")
}

/**
 * Manages the state and visibility of a local bottom sheet.
 *
 * This class provides methods to show and hide the bottom sheet, as well as access to its visibility state.
 * It works in conjunction with a [LocalBottomSheetViewModel] to store the current screen and visibility.
 *
 * @param viewModel The [LocalBottomSheetViewModel] instance associated with the bottom sheet.
 * @param sheetState The [SheetState] of the bottom sheet, used for controlling its animation and state.
 * @param scope The coroutine scope used for launching asynchronous operations related to the bottom sheet.
 *
 * @constructor Creates a new instance of [LocalBottomSheetManager].
 */
class LocalBottomSheetManager @OptIn(ExperimentalMaterial3Api::class) constructor(
    private val viewModel: LocalBottomSheetViewModel,
    private val sheetState: SheetState,
    private val scope: CoroutineScope
) {
    val isVisible: Boolean
        get() = viewModel.isVisible

    @OptIn(ExperimentalMaterial3Api::class)
    fun show(screen: BottomSheet<*>) {
        scope.launch {
            viewModel.updateScreen(screen)
            viewModel.updateVisibility(true)
            sheetState.show()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun hide() {
        scope.launch {
            viewModel.updateVisibility(false)
            viewModel.updateScreen(null)
            sheetState.hide()
        }
    }
}