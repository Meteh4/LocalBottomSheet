package com.metoly.localbottomsheet.presentation.test_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metoly.localbottomsheet.model.BottomSheet
import com.metoly.localbottomsheet.utils.LocalBottomSheet

class TestBottomSheet : BottomSheet<TestBottomSheetViewModel>(
    viewModelClass = TestBottomSheetViewModel::class.java,
    scopeName = "test_sheet_viewmodel"
) {
    @Composable
    override fun SheetContent() {
        val localBottomSheet = LocalBottomSheet.current
        val counter by viewModel.count.collectAsStateWithLifecycle()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        ) {
            Text(
                text = counter.toString(),
                style = TextStyle(
                    fontSize = 48.sp
                )
            )

            Button(onClick = localBottomSheet::hide) {
                Text(text = "Hide Sheet")
            }
        }
    }
}