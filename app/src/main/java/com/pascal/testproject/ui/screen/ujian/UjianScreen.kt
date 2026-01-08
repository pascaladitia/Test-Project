package com.pascal.testproject.ui.screen.ujian

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
fun UjianScreen(vm: AkademikViewModel) {
    val ujian by vm.ujian.collectAsState(emptyList())
    var nama by remember { mutableStateOf("") }
    var idMatpel by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("CRUD UJIAN", fontWeight = FontWeight.Bold)

        OutlinedTextField(nama, { nama = it }, label = { Text("Nama Ujian") })
        OutlinedTextField(idMatpel, { idMatpel = it }, label = { Text("ID Matpel") })

        Button(onClick = {
            vm.tambahUjian(
                nama,
                idMatpel.toInt(),
                System.currentTimeMillis()
            )
        }) {
            Text("Tambah")
        }

        HorizontalDivider()

        ujian.forEach {
            Text("${it.namaUjian} | Matpel:${it.idMatpel}")
        }
    }
}
