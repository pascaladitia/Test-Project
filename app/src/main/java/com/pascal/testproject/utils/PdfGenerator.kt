package com.pascal.testproject.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.UjianRekap
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfGenerator(private val context: Context) {

    fun generateLaporan(
        rekap: List<UjianRekap>,
        lulus: Int,
        gagal: List<SiswaGagal>
    ) {
        val file = File(
            context.getExternalFilesDir(null),
            "laporan_akademik_${System.currentTimeMillis()}.pdf"
        )

        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val doc = Document(pdf)

        doc.add(
            Paragraph("LAPORAN SISTEM INFORMASI AKADEMIK")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16f)
        )

        doc.add(
            Paragraph(
                "Tanggal Cetak: ${
                    SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                    ).format(Date())
                }"
            ).setTextAlignment(TextAlignment.CENTER)
        )

        doc.add(Paragraph("\nJumlah Siswa Lulus: $lulus").setBold())

        doc.add(Paragraph("\nREKAP UJIAN").setBold())

        val rekapTable = Table(floatArrayOf(3f, 3f, 2f))
        rekapTable.addCell("Nama Ujian")
        rekapTable.addCell("Mata Pelajaran")
        rekapTable.addCell("Jumlah Peserta")

        rekap.forEach {
            rekapTable.addCell(it.namaUjian)
            rekapTable.addCell(it.namaMatpel)
            rekapTable.addCell(it.jumlahPeserta.toString())
        }

        doc.add(rekapTable)

        doc.add(Paragraph("\nSISWA TIDAK LULUS").setBold())

        if (gagal.isEmpty()) {
            doc.add(Paragraph("Tidak ada siswa yang gagal"))
        } else {
            val gagalTable = Table(floatArrayOf(4f, 4f))
            gagalTable.addCell("Nama Siswa")
            gagalTable.addCell("Mata Pelajaran")

            gagal.forEach {
                gagalTable.addCell(it.namaSiswa)
                gagalTable.addCell(it.namaMatpel)
            }

            doc.add(gagalTable)
        }

        doc.close()

        openPdf(file)
    }

    private fun openPdf(file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }
}
