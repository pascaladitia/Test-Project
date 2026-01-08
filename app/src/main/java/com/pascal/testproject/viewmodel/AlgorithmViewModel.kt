package com.pascal.testproject.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.Period
import java.time.Year.isLeap
import java.time.format.DateTimeFormatter

class AlgorithmViewModel : ViewModel() {

    private val _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    private fun reverseRecursive(text: String): String {
        if (text.isEmpty()) return ""
        return reverseRecursive(text.substring(1)) + text[0]
    }

    fun runReverse(text: String) {
        _output.value = "Hasil: ${reverseRecursive(text)}"
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..kotlin.math.sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    fun runPrime(n: Int) {
        val primes = (2 until n).filter { isPrime(it) }
        _output.value =
            "Deret: ${primes.joinToString(", ")}\nJumlah: ${primes.sum()}"
    }

    @SuppressLint("NewApi")
    fun runAge(date: String) {
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val birth = LocalDate.parse(date, formatter)
        val now = LocalDate.now()
        val period = Period.between(birth, now)

        val leapYears = (birth.year..now.year).count {
            isLeap(it.toLong())
        }

        _output.value =
            "${period.years} tahun, ${period.months} bulan, ${period.days} hari\n" +
                    "$leapYears tahun kabisat"
    }

    fun runDuplicate(list: String) {
        val nums = list.split(",").map { it.trim().toInt() }
        val duplicates = nums.groupBy { it }
            .filter { it.value.size > 1 }
            .keys
        _output.value = "Angka ganda: ${duplicates.joinToString(", ")}"
    }
}
