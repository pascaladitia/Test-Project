package com.pascal.testproject.ui.screen.siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun SiswaScreen(vm: AkademikViewModel) {
    val siswaList by vm.siswa.collectAsState(emptyList())
    var selected by remember { mutableStateOf<SiswaEntity?>(null) }

    var nis by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("DATA SISWA", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = nis,
            onValueChange = { nis = it },
            label = { Text("NIS") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nis.isNotBlank() && nama.isNotBlank()) {
                    vm.tambahSiswa(nis, nama, alamat)
                    nis = ""
                    nama = ""
                    alamat = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tambah Siswa")
        }

        Divider(Modifier.padding(vertical = 12.dp))

        // ===== LIST SISWA =====
        siswaList.forEach { siswa ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${siswa.nis} - ${siswa.nama}")

                Row {
                    Button(onClick = { selected = siswa }) {
                        Text("Edit")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { vm.deleteSiswa(siswa) }) {
                        Text("Hapus")
                    }
                }
            }
        }
    }

    // ===== DIALOG EDIT =====
    selected?.let {
        EditSiswaDialog(
            siswa = it,
            onDismiss = { selected = null },
            onSave = {
                vm.updateSiswa(it)
                selected = null
            }
        )
    }
}
