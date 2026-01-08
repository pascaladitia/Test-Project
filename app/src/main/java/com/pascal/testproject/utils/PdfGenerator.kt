package com.pascal.testproject.utils

import android.content.Context
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.UjianRekap
import java.io.File

class PdfGenerator(private val context: Context) {

    fun generateLaporan(
        rekap: List<UjianRekap>,
        lulus: Int,
        gagal: List<SiswaGagal>
    ) {
        val file = File(
            context.getExternalFilesDir(null),
            "laporan_akademik.pdf"
        )

        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val doc = Document(pdf)

        doc.add(Paragraph("LAPORAN SISTEM INFORMASI AKADEMIK"))
        doc.add(Paragraph("Jumlah Siswa Lulus: $lulus"))
        doc.add(Paragraph("\nREKAP UJIAN"))

        rekap.forEach {
            doc.add(
                Paragraph(
                    "${it.namaUjian} | ${it.namaMatpel} | Peserta: ${it.jumlahPeserta}"
                )
            )
        }

        doc.add(Paragraph("\nSISWA TIDAK LULUS"))
        gagal.forEach {
            doc.add(
                Paragraph("${it.namaSiswa} gagal pada ${it.namaMatpel}")
            )
        }

        doc.close()
    }
}
