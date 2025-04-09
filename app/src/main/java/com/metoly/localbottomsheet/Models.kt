package com.metoly.localbottomsheet

import com.metoly.localbottomsheet.presentation.test_bottom_sheet.TestBottomSheetViewModel
import com.metoly.localbottomsheet.utils.LocalBottomSheetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val modules = module {
    viewModelOf(::LocalBottomSheetViewModel)
    viewModelOf(::TestBottomSheetViewModel)
    scope(named("test_sheet_viewmodel")) {
        scoped {
            TestBottomSheetViewModel()
        }
    }
}