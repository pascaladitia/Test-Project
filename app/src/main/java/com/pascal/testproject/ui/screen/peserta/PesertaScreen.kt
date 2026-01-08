package com.pascal.testproject.ui.screen.peserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.ui.screen.component.FormDialog
import com.pascal.testproject.ui.screen.component.FormDialogField
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun PesertaScreen(vm: AkademikViewModel) {
    val peserta by vm.peserta.collectAsState(emptyList())

    var nis by remember { mutableStateOf("") }
    var idUjian by remember { mutableStateOf("") }
    var nilai by remember { mutableStateOf("") }

    var selected by remember { mutableStateOf<PesertaEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nis = ""
                idUjian = ""
                nilai = ""
                showAddDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Peserta")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "PESERTA UJIAN",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            peserta.forEach { item ->
                val status = if (item.nilai >= 75) "LULUS" else "TIDAK LULUS"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("NIS: ${item.nis}", fontWeight = FontWeight.Medium)
                            Text("Ujian ID: ${item.idUjian}")
                            Text("Nilai: ${item.nilai} ($status)")
                        }

                        Row {
                            IconButton(onClick = {
                                selected = item
                                nis = item.nis
                                idUjian = item.idUjian.toString()
                                nilai = item.nilai.toString()
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = {
                                vm.deletePeserta(item)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Hapus")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        FormDialog(
            title = "Tambah Peserta",
            fields = listOf(
                FormDialogField(
                    label = "NIS",
                    value = nis,
                    icon = Icons.Default.Person,
                    onValueChange = { nis = it }
                ),
                FormDialogField(
                    label = "ID Ujian",
                    value = idUjian,
                    icon = Icons.Default.Assignment,
                    onValueChange = { idUjian = it }
                ),
                FormDialogField(
                    label = "Nilai",
                    value = nilai,
                    icon = Icons.Default.Grade,
                    onValueChange = { nilai = it }
                )
            ),
            onConfirm = {
                if (nis.isNotBlank() && idUjian.isNotBlank() && nilai.isNotBlank()) {
                    vm.tambahPeserta(
                        nis = nis,
                        idUjian = idUjian.toInt(),
                        nilai = nilai.toInt()
                    )
                    showAddDialog = false
                }
            },
            onDismiss = {
                showAddDialog = false
            }
        )
    }

    if (showEditDialog && selected != null) {
        FormDialog(
            title = "Edit Peserta",
            fields = listOf(
                FormDialogField(
                    label = "Nilai",
                    value = nilai,
                    icon = Icons.Default.Grade,
                    onValueChange = { nilai = it }
                )
            ),
            onConfirm = {
                vm.updatePeserta(
                    selected!!.copy(nilai = nilai.toInt())
                )
                selected = null
                showEditDialog = false
            },
            onDismiss = {
                selected = null
                showEditDialog = false
            }
        )
    }
}
