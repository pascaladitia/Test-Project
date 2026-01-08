package com.pascal.testproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pascal.testproject.ui.screen.algorithm.AlgorithmScreen
import com.pascal.testproject.ui.screen.home.HomeScreen
import com.pascal.testproject.ui.screen.laporan.LaporanScreen
import com.pascal.testproject.ui.screen.matpel.MatpelScreen
import com.pascal.testproject.ui.screen.peserta.PesertaScreen
import com.pascal.testproject.ui.screen.siswa.SiswaScreen
import com.pascal.testproject.ui.screen.ujian.UjianScreen
import com.pascal.testproject.viewmodel.AkademikViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph() {
    val nav = rememberNavController()
    val vm: AkademikViewModel = koinViewModel()

    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(nav) }
        composable(Routes.SISWA) { SiswaScreen(vm) }
        composable(Routes.MATPEL) { MatpelScreen(vm) }
        composable(Routes.UJIAN) { UjianScreen(vm) }
        composable(Routes.PESERTA) { PesertaScreen(vm) }
        composable(Routes.LAPORAN) { LaporanScreen(vm) }
        composable(Routes.ALGORITMA) { AlgorithmScreen() }
    }
}
