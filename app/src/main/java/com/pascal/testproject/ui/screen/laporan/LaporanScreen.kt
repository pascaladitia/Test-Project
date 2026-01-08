package com.pascal.testproject.ui.screen.laporan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.utils.PdfGenerator
import com.pascal.testproject.viewmodel.AkademikViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanScreen(vm: AkademikViewModel) {
    val context = LocalContext.current
    val pdf = remember { PdfGenerator(context) }

    val rekap by vm.rekapByTanggal.collectAsState(emptyList())
    val lulus by vm.jumlahLulus.collectAsState(0)
    val gagal by vm.gagal.collectAsState(emptyList())

    val totalPeserta = rekap.sumOf { it.jumlahPeserta }
    val persentase = if (totalPeserta == 0) 0 else (lulus * 100 / totalPeserta)

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    val startPickerState = rememberDatePickerState()
    val endPickerState = rememberDatePickerState()

    Column(Modifier.padding(16.dp)) {

        Text("LAPORAN", fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        Button(onClick = { showStartPicker = true }) {
            Text(startDate?.let { "Mulai: ${formatDate(it)}" } ?: "Pilih Tanggal Mulai")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { showEndPicker = true }) {
            Text(endDate?.let { "Sampai: ${formatDate(it)}" } ?: "Pilih Tanggal Akhir")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            enabled = startDate != null && endDate != null,
            onClick = {
                vm.loadRekapByTanggal(startDate!!, endDate!!)
            }
        ) {
            Text("Filter")
        }

        Spacer(Modifier.height(16.dp))


        Text("Jumlah Siswa Lulus: $lulus")

        Spacer(Modifier.height(8.dp))
        Text("Rekap Ujian:")
        rekap.forEach {
            Text(
                "${it.namaUjian} | ${it.namaMatpel} | ${
                    formatDate(it.tanggal)
                } | ${it.jumlahPeserta} peserta"
            )
        }

        Spacer(Modifier.height(8.dp))
        Text("Siswa Tidak Lulus:")
        gagal.forEach {
            Text("${it.namaSiswa} gagal di ${it.namaMatpel}")
        }

        Spacer(Modifier.height(8.dp))
        Text("Total Peserta Ujian: $totalPeserta")

        Spacer(Modifier.height(8.dp))
        Text("Persentase Kelulusan: $persentase%")

        Spacer(Modifier.height(24.dp))

        Button(onClick = {
            pdf.generateLaporan(rekap, lulus, gagal)
        }) {
            Text("Export PDF")
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

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(millis))
