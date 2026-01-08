package com.pascal.testproject.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class AlgorithmViewModel : ViewModel() {

    private val _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    fun runReverse(input: String) {
        if (input.isBlank()) {
            _output.value = "Error: Input tidak boleh kosong"
            return
        }
        _output.value = "Hasil: ${input.reversed()}"
    }

    fun runPrime(input: String) {
        val n = input.toIntOrNull()
        if (n == null || n < 2) {
            _output.value = "Error: Masukkan angka >= 2"
            return
        }

        val primes = (2 until n).filter { num ->
            (2..kotlin.math.sqrt(num.toDouble()).toInt()).all { num  != 0 }
        }

        _output.value = "Deret: ${primes.joinToString(", ")}\nJumlah: ${primes.sum()}"
    }

    @SuppressLint("NewApi")
    fun runAge(input: String) {
        try {
            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
            val birth = LocalDate.parse(input, formatter)
            val now = LocalDate.now()

            if (birth.isAfter(now)) {
                _output.value = "Error: Tanggal lahir tidak valid"
                return
            }

            val period = Period.between(birth, now)

            val leapYears = (birth.year..now.year).count {
                LocalDate.of(it, 1, 1).isLeapYear
            }

            _output.value =
                "${period.years} tahun, ${period.months} bulan, ${period.days} hari\n" +
                        "$leapYears tahun kabisat"
        } catch (e: Exception) {
            _output.value = "Error: Format tanggal MM-DD-YYYY"
        }
    }

    fun runDuplicate(input: String) {
        val nums = input.split(",")
            .mapNotNull { it.trim().toIntOrNull() }

        if (nums.isEmpty()) {
            _output.value = "Error: Input harus angka dipisahkan koma"
            return
        }

        val duplicates = nums.groupBy { it }
            .filter { it.value.size > 1 }
            .keys

        _output.value =
            if (duplicates.isEmpty())
                "Tidak ada angka ganda"
            else
                "Angka ganda: ${duplicates.joinToString(", ")}"
    }
}
