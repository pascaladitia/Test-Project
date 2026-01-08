package com.pascal.testproject.ui.screen.laporan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pascal.testproject.utils.PdfGenerator
import com.pascal.testproject.viewmodel.AkademikViewModel

@Composable
fun LaporanScreen(vm: AkademikViewModel) {
    val context = LocalContext.current
    val pdf = remember { PdfGenerator(context) }


    val rekap by vm.rekap.collectAsState(emptyList())
    val lulus by vm.jumlahLulus.collectAsState(0)
    val gagal by vm.gagal.collectAsState(emptyList())
    val totalPeserta = rekap.sumOf { it.jumlahPeserta }

    Column(Modifier.padding(16.dp)) {
        Text("LAPORAN", fontWeight = FontWeight.Bold)

        Text("Jumlah Siswa Lulus: $lulus")

        Spacer(Modifier.height(8.dp))
        Text("Rekap Ujian:")
        rekap.forEach {
            Text("${it.namaUjian} | ${it.namaMatpel} | ${it.jumlahPeserta} peserta")
        }

        Spacer(Modifier.height(8.dp))
        Text("Siswa Tidak Lulus:")
        gagal.forEach {
            Text("${it.namaSiswa} gagal di ${it.namaMatpel}")
        }

        Spacer(Modifier.height(8.dp))

        Text("Total Peserta Ujian: $totalPeserta")

        Spacer(Modifier.height(8.dp))

        val persentase = if (totalPeserta == 0) 0
        else (lulus * 100 / totalPeserta)

        Text("Persentase Kelulusan: $persentase%")

        Spacer(Modifier.height(24.dp))

        Button(onClick = {
            pdf.generateLaporan(rekap, lulus, gagal)
        }) {
            Text("Export PDF")
        }
    }
}
