package com.pascal.testproject.ui.screen.peserta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
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
fun PesertaScreen(vm: AkademikViewModel) {
    val peserta by vm.peserta.collectAsState(emptyList())
    var nis by remember { mutableStateOf("") }
    var idUjian by remember { mutableStateOf("") }
    var nilai by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("PESERTA UJIAN", fontWeight = FontWeight.Bold)

        OutlinedTextField(nis, { nis = it }, label = { Text("NIS") })
        OutlinedTextField(idUjian, { idUjian = it }, label = { Text("ID Ujian") })
        OutlinedTextField(nilai, { nilai = it }, label = { Text("Nilai") })

        Button(onClick = {
            vm.tambahPeserta(nis, idUjian.toInt(), nilai.toInt())
        }) {
            Text("Simpan")
        }

        HorizontalDivider()

        peserta.forEach {
            val status = if (it.nilai >= 75) "LULUS" else "TIDAK LULUS"
            Text("${it.nis} | Nilai: ${it.nilai} → $status")
        }
    }
}
