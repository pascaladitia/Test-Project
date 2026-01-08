package com.pascal.testproject.ui.screen.siswa

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.pascal.testproject.data.entity.SiswaEntity

@Composable
fun EditSiswaDialog(
    siswa: SiswaEntity,
    onDismiss: () -> Unit,
    onSave: (SiswaEntity) -> Unit
) {
    var nama by remember { mutableStateOf(siswa.nama) }
    var alamat by remember { mutableStateOf(siswa.alamat) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(siswa.copy(nama = nama, alamat = alamat))
            }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        },
        title = { Text("Edit Siswa") },
        text = {
            Column {
                OutlinedTextField(nama, { nama = it }, label = { Text("Nama") })
                OutlinedTextField(alamat, { alamat = it }, label = { Text("Alamat") })
            }
        }
    )
}
