package com.metoly.localbottomsheet.presentation.test_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metoly.localbottomsheet.presentation.test_bottom_sheet.TestBottomSheet
import com.metoly.localbottomsheet.utils.LocalBottomSheet

@Composable
fun TestScreen() {
    val localBottomSheet = LocalBottomSheet.current

    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = {
                    localBottomSheet.show(
                        TestBottomSheet()
                    )
                }
            ) {
                Text(text = "Show Counter Bottom Sheet")
            }
        }
    }
}