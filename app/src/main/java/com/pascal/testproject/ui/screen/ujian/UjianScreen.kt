package com.pascal.testproject.ui.screen.ujian

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.data.entity.UjianEntity
import com.pascal.testproject.ui.screen.component.FormDialog
import com.pascal.testproject.ui.screen.component.FormDialogField
import com.pascal.testproject.viewmodel.AkademikViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UjianScreen(vm: AkademikViewModel) {
    val ujian by vm.ujian.collectAsState(emptyList())
    val context = LocalContext.current

    var nama by remember { mutableStateOf("") }
    var idMatpel by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf(System.currentTimeMillis()) }

    var selected by remember { mutableStateOf<UjianEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    val dateFormatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    fun openDatePicker(onSelected: (Long) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, y, m, d ->
                cal.set(y, m, d, 0, 0, 0)
                onSelected(cal.timeInMillis)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nama = ""
                idMatpel = ""
                tanggal = System.currentTimeMillis()
                showAddDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Ujian")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "UJIAN",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            ujian.forEach { item ->
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
                            Text(item.namaUjian, fontWeight = FontWeight.Medium)
                            Text("Matpel ID: ${item.idMatpel}")
                            Text("Tanggal: ${dateFormatter.format(Date(item.tanggal))}")
                        }

                        Row {
                            IconButton(onClick = {
                                selected = item
                                nama = item.namaUjian
                                idMatpel = item.idMatpel.toString()
                                tanggal = item.tanggal
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = {
                                vm.deleteUjian(item)
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
            title = "Tambah Ujian",
            fields = listOf(
                FormDialogField(
                    label = "Nama Ujian",
                    value = nama,
                    onValueChange = { nama = it }
                ),
                FormDialogField(
                    label = "ID Mata Pelajaran",
                    value = idMatpel,
                    onValueChange = { idMatpel = it }
                ),
                FormDialogField(
                    label = "Tanggal",
                    value = dateFormatter.format(Date(tanggal)),
                    icon = Icons.Default.CalendarToday,
                    onValueChange = {}
                )
            ),
            onConfirm = {
                vm.tambahUjian(
                    nama = nama,
                    idMatpel = idMatpel.toInt(),
                    tanggal = tanggal
                )
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )

        LaunchedEffect(Unit) {
            openDatePicker { tanggal = it }
        }
    }

    if (showEditDialog && selected != null) {
        FormDialog(
            title = "Edit Ujian",
            fields = listOf(
                FormDialogField(
                    label = "Nama Ujian",
                    value = nama,
                    onValueChange = { nama = it }
                ),
                FormDialogField(
                    label = "Tanggal",
                    value = dateFormatter.format(Date(tanggal)),
                    icon = Icons.Default.CalendarToday,
                    onValueChange = {}
                )
            ),
            onConfirm = {
                vm.updateUjian(
                    selected!!.copy(
                        namaUjian = nama,
                        tanggal = tanggal
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

        LaunchedEffect(Unit) {
            openDatePicker { tanggal = it }
        }
    }
}