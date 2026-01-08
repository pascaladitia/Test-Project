package com.pascal.testproject.ui.screen.ujian

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun UjianFilterScreen(vm: AkademikViewModel) {
    var start by remember { mutableStateOf(0L) }
    var end by remember { mutableStateOf(System.currentTimeMillis()) }

    val ujian by vm.ujianByTanggal(start, end)
        .collectAsState(initial = emptyList())

    Column(Modifier.padding(16.dp)) {
        Text("Ujian Berdasarkan Tanggal", fontWeight = FontWeight.Bold)

        Button(onClick = {
            start = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        }) {
            Text("7 Hari Terakhir")
        }

        HorizontalDivider()

        ujian.forEach {
            Text(it.namaUjian)
        }
    }
}
