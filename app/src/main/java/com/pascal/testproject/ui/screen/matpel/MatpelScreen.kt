package com.pascal.testproject.ui.screen.matpel

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
fun MatpelScreen(vm: AkademikViewModel) {
    val matpel by vm.matpel.collectAsState(emptyList())
    var nama by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("CRUD MATA PELAJARAN", fontWeight = FontWeight.Bold)

        OutlinedTextField(nama, { nama = it }, label = { Text("Nama Matpel") })

        Button(onClick = { vm.tambahMatpel(nama) }) {
            Text("Tambah")
        }

        HorizontalDivider()

        matpel.forEach {
            Text("${it.idMatpel} - ${it.namaMatpel}")
        }
    }
}
