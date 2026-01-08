package com.pascal.testproject.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pascal.testproject.ui.navigation.Routes

@Composable
fun HomeScreen(nav: NavController) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("SISTEM INFORMASI AKADEMIK", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Button(onClick = { nav.navigate(Routes.SISWA) }) { Text("Data Siswa") }
        Button(onClick = { nav.navigate(Routes.MATPEL) }) { Text("Mata Pelajaran") }
        Button(onClick = { nav.navigate(Routes.UJIAN) }) { Text("Ujian") }
        Button(onClick = { nav.navigate(Routes.PESERTA) }) { Text("Peserta & Nilai") }
        Button(onClick = { nav.navigate(Routes.LAPORAN) }) { Text("Laporan") }
    }
}
