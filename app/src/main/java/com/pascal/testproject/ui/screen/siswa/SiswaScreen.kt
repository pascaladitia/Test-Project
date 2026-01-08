package com.pascal.testproject.ui.screen.siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.ui.screen.component.FormDialog
import com.pascal.testproject.ui.screen.component.FormDialogField
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun SiswaScreen(vm: AkademikViewModel) {
    val siswaList by vm.siswa.collectAsState(emptyList())

    var nis by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    var selected by remember { mutableStateOf<SiswaEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nis = ""
                nama = ""
                alamat = ""
                showAddDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "DATA SISWA",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            siswaList.forEach { siswa ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(siswa.nama, fontWeight = FontWeight.SemiBold)
                            Text(
                                text = "NIS: ${siswa.nis}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = siswa.alamat,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Row {
                            IconButton(onClick = {
                                selected = siswa
                                nis = siswa.nis
                                nama = siswa.nama
                                alamat = siswa.alamat
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = {
                                vm.deleteSiswa(siswa)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Hapus")
                            }
                        }
                    }
                }
            }
        }
    }

    // ===== DIALOG TAMBAH =====
    if (showAddDialog) {
        FormDialog(
            title = "Tambah Siswa",
            fields = listOf(
                FormDialogField(
                    label = "NIS",
                    value = nis,
                    icon = Icons.Default.Badge,
                    onValueChange = { nis = it }
                ),
                FormDialogField(
                    label = "Nama",
                    value = nama,
                    icon = Icons.Default.Person,
                    onValueChange = { nama = it }
                ),
                FormDialogField(
                    label = "Alamat",
                    value = alamat,
                    icon = Icons.Default.Home,
                    onValueChange = { alamat = it }
                )
            ),
            onConfirm = {
                if (nis.isNotBlank() && nama.isNotBlank()) {
                    vm.tambahSiswa(nis, nama, alamat)
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
            title = "Edit Siswa",
            fields = listOf(
                FormDialogField(
                    label = "Nama",
                    value = nama,
                    icon = Icons.Default.Person,
                    onValueChange = { nama = it }
                ),
                FormDialogField(
                    label = "Alamat",
                    value = alamat,
                    icon = Icons.Default.Home,
                    onValueChange = { alamat = it }
                )
            ),
            onConfirm = {
                vm.updateSiswa(
                    selected!!.copy(
                        nama = nama,
                        alamat = alamat
                    )
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
