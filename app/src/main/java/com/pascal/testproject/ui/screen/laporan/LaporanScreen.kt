package com.pascal.testproject.ui.screen.laporan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.utils.PdfGenerator
import com.pascal.testproject.viewmodel.AkademikViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanScreen(vm: AkademikViewModel) {
    val context = LocalContext.current
    val pdf = remember { PdfGenerator(context) }

    val rekap by vm.rekapByTanggal.collectAsState(emptyList())
    val jumlahLulus by vm.jumlahLulus.collectAsState(0)
    val lulus by vm.lulus.collectAsState(emptyList())
    val gagal by vm.gagal.collectAsState(emptyList())

    val totalPeserta = rekap.sumOf { it.jumlahPeserta }
    val persentase = if (totalPeserta == 0) 0 else (jumlahLulus * 100 / totalPeserta)

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    val startPickerState = rememberDatePickerState()
    val endPickerState = rememberDatePickerState()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Laporan Akademik",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Filter Tanggal",
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { showStartPicker = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.CalendarMonth, null)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    startDate?.let { formatDate(it) }
                                        ?: "Tanggal Mulai"
                                )
                            }

                            OutlinedButton(
                                onClick = { showEndPicker = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.CalendarMonth, null)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    endDate?.let { formatDate(it) }
                                        ?: "Tanggal Akhir"
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            enabled = startDate != null && endDate != null,
                            onClick = {
                                vm.loadRekapByTanggal(startDate!!, endDate!!)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.FilterAlt, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Terapkan Filter")
                        }
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(
                        title = "Lulus",
                        value = jumlahLulus.toString(),
                        icon = Icons.Default.CheckCircle,
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Tidak Lulus",
                        value = gagal.size.toString(),
                        icon = Icons.Default.Error,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                StatCard(
                    title = "Persentase Kelulusan",
                    value = "$persentase%",
                    icon = Icons.Default.Description,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (rekap.isNotEmpty()) {
                item {
                    Text(
                        "Rekap Ujian",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(rekap.size) { i ->
                val item = rekap[i]
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(item.namaUjian, fontWeight = FontWeight.SemiBold)
                        Text("Matpel: ${item.namaMatpel}")
                        Text("Tanggal: ${formatDate(item.tanggal)}")
                        Text("Peserta: ${item.jumlahPeserta}")
                    }
                }
            }

            if (lulus.isNotEmpty()) {
                item {
                    Text(
                        "Siswa Lulus",
                        fontWeight = FontWeight.Bold
                    )
                }

                items(lulus.size) { i ->
                    val l = lulus[i]
                    Text("• ${l.namaSiswa} – ${l.namaMatpel}")
                }

                item {
                    Spacer(Modifier.height(16.dp))
                }
            }

            if (gagal.isNotEmpty()) {
                item {
                    Text(
                        "Siswa Tidak Lulus",
                        fontWeight = FontWeight.Bold
                    )
                }

                items(gagal.size) { i ->
                    val g = gagal[i]
                    Text("• ${g.namaSiswa} – ${g.namaMatpel}")
                }
            }

            item {
                Button(
                    onClick = {
                        pdf.generateLaporan(rekap, jumlahLulus, gagal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PictureAsPdf, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Export PDF")
                }
            }
        }
    }

    if (showStartPicker) {
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                Button(onClick = {
                    startDate = startPickerState.selectedDateMillis
                    showStartPicker = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = startPickerState)
        }
    }

    if (showEndPicker) {
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                Button(onClick = {
                    endDate = endPickerState.selectedDateMillis
                    showEndPicker = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = endPickerState)
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, style = MaterialTheme.typography.bodySmall)
                Text(
                    value,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(millis))
