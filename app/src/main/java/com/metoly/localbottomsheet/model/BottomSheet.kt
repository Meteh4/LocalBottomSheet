package com.metoly.localbottomsheet.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.core.scope.get

/**
 * Base class for Bottom Sheets that utilize Koin for dependency injection and scoping.
 *
 * This class provides a standardized way to manage ViewModel instances and other dependencies
 * within the scope of a bottom sheet. It automatically creates and closes a Koin scope
 * associated with the bottom sheet, ensuring that dependencies are properly managed.
 *
 * @param VM The type of the ViewModel associated with the bottom sheet.
 * @param viewModelClass The Class object representing the ViewModel type.
 * @param scopeName A unique name for the Koin scope associated with this bottom sheet.
 */
abstract class BottomSheetScreen<VM : ViewModel>(
    private val viewModelClass: Class<VM>,
    private val scopeName: String
) : KoinComponent {

    private val scope: Scope by lazy {
        getKoin().getOrCreateScope(scopeName, named(scopeName))
    }

    protected val viewModel: VM by lazy {
        scope.get(viewModelClass)
    }

    fun closeScope() {
        scope.close()
    }

    protected fun <T> getScopedInstance(clazz: Class<T>): T {
        return scope.get(clazz)
    }

    protected fun <T> getScopedInstanceWithParams(clazz: Class<T>, vararg parameters: Any): T {
        return scope.get(clazz) { parametersOf(*parameters) }
    }
}

/**
 * An abstract base class for creating bottom sheet screens using Jetpack Compose.
 *
 * This class provides a foundation for building bottom sheet screens by handling ViewModel
 * instantiation and providing a composable function for defining the screen's content.
 *
 * @param VM The type of the ViewModel associated with the screen.
 * @param viewModelClass The class of the ViewModel.
 * @param scopeName The name of the scope for the ViewModel. This is used to identify the
 * ViewModel instance when retrieving it using `viewModel()`.
 *
 * Usage:
 *
 * ```kotlin
 * class MyBottomSheetScreen : BottomSheetScreen<MyViewModel>(MyViewModel::class.java, "MyBottomSheetScreen") {
 *     @Composable
 *     override fun ScreenContent() {
 *         // Define the content of the bottom sheet here
 *     }
 * }
 * ```
 */
abstract class BottomSheet<VM : ViewModel>(
    viewModelClass: Class<VM>,
    scopeName: String
) : BottomSheetScreen<VM>(viewModelClass, scopeName) {

    @Composable
    abstract fun SheetContent()
}