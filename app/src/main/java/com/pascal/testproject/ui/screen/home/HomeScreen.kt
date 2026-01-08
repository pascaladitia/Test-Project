package com.pascal.testproject.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pascal.testproject.ui.navigation.Routes

@Composable
fun HomeScreen(nav: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "SISTEM INFORMASI AKADEMIK",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        DashboardButton(
            title = "Data Siswa",
            icon = Icons.Default.Person,
            onClick = { nav.navigate(Routes.SISWA) }
        )
        DashboardButton(
            title = "Mata Pelajaran",
            icon = Icons.Default.MenuBook,
            onClick = { nav.navigate(Routes.MATPEL) }
        )
        DashboardButton(
            title = "Ujian",
            icon = Icons.Default.Event,
            onClick = { nav.navigate(Routes.UJIAN) }
        )
        DashboardButton(
            title = "Peserta & Nilai",
            icon = Icons.Default.Assessment,
            onClick = { nav.navigate(Routes.PESERTA) }
        )
        DashboardButton(
            title = "Laporan",
            icon = Icons.Default.PictureAsPdf,
            onClick = { nav.navigate(Routes.LAPORAN) }
        )
        DashboardButton(
            title = "Algoritma",
            icon = Icons.Default.Code,
            onClick = { nav.navigate(Routes.ALGORITMA) }
        )
    }
}

@Composable
fun DashboardButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}