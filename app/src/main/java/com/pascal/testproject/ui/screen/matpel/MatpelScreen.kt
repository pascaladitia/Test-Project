package com.pascal.testproject.ui.screen.matpel

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.ui.screen.component.FormDialog
import com.pascal.testproject.ui.screen.component.FormDialogField
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun MatpelScreen(vm: AkademikViewModel) {
    val matpel by vm.matpel.collectAsState(emptyList())

    var nama by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<MataPelajaranEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nama = ""
                showAddDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "MATA PELAJARAN",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            matpel.forEach { item ->
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
                        Text(item.namaMatpel, fontWeight = FontWeight.Medium)

                        Row {
                            IconButton(onClick = {
                                selected = item
                                nama = item.namaMatpel
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = {
                                vm.deleteMatpel(item)
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
            title = "Tambah Mata Pelajaran",
            fields = listOf(
                FormDialogField(
                    label = "Nama Mata Pelajaran",
                    value = nama,
                    icon = Icons.Default.Book,
                    onValueChange = { nama = it }
                )
            ),
            onConfirm = {
                if (nama.isNotBlank()) {
                    vm.tambahMatpel(nama)
                    nama = ""
                    showAddDialog = false
                }
            },
            onDismiss = {
                nama = ""
                showAddDialog = false
            }
        )
    }

    if (showEditDialog && selected != null) {
        FormDialog(
            title = "Edit Mata Pelajaran",
            fields = listOf(
                FormDialogField(
                    label = "Nama Mata Pelajaran",
                    value = nama,
                    icon = Icons.Default.Book,
                    onValueChange = { nama = it }
                )
            ),
            onConfirm = {
                vm.updateMatpel(selected!!.copy(namaMatpel = nama))
                selected = null
                nama = ""
                showEditDialog = false
            },
            onDismiss = {
                selected = null
                nama = ""
                showEditDialog = false
            }
        )
    }
}
