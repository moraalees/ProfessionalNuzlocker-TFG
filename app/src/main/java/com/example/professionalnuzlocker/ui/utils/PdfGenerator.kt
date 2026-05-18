package com.example.professionalnuzlocker.ui.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.graphics.scale
import com.example.professionalnuzlocker.ui.screens.stats.EstadisticasData
import java.io.File
import java.io.FileOutputStream

private const val PAGE_W = 595
private const val PAGE_H = 842
private const val MARGIN = 30f
private const val CONTENT_BOTTOM = PAGE_H - 32f

private val PDF_BG        = 0xFF121212.toInt()
private val PDF_HEADER_BG = 0xFF1A0A0A.toInt()
private val PDF_ROW_BG    = 0xFF1C1818.toInt()
private val PDF_ACCENT    = 0xFFE7193E.toInt()
private val PDF_ACCENT_DK = 0xFF851122.toInt()
private val PDF_TEXT      = 0xFFE0E0E0.toInt()
private val PDF_MUTED     = 0xFF888888.toInt()
private val PDF_WHITE     = 0xFFFFFFFF.toInt()
private val PDF_GOLD      = 0xFFFFD700.toInt()
private val PDF_SILVER    = 0xFFC0C0C0.toInt()
private val PDF_BRONZE    = 0xFFCD7F32.toInt()
private val PDF_VICTORY   = 0xFF4CAF50.toInt()
private val PDF_BLACK     = 0xFF000000.toInt()

fun generarPDF(context: Context, datos: EstadisticasData) {
    val documento = PdfDocument()
    var numeroPagina = 1
    var pagina = documento.startPage(PdfDocument.PageInfo.Builder(PAGE_W, PAGE_H, numeroPagina).create())
    var canvas = pagina.canvas
    var ejeY = 0f

    fun generarRectangulo() = canvas.drawRect(0f, 0f, PAGE_W.toFloat(), PAGE_H.toFloat(), rellenarColor(PDF_BG))

    fun generarHeader() {
        canvas.drawRect(0f, 0f, PAGE_W.toFloat(), 78f, rellenarColor(PDF_HEADER_BG))
        canvas.drawRect(0f, 0f, 6f, 78f, rellenarColor(PDF_ACCENT))
        canvas.drawLine(0f, 78f, PAGE_W.toFloat(), 78f, generarBorde(PDF_ACCENT, 2.5f))
        canvas.drawText(
            "PROFESSIONAL NUZLOCKER",
            PAGE_W / 2f, 38f,
            generarTexto(22f, PDF_ACCENT, bold = true, align = Paint.Align.CENTER)
        )
        canvas.drawText(
            "Resumen de Aventura",
            PAGE_W / 2f, 58f,
            generarTexto(11f, PDF_MUTED, align = Paint.Align.CENTER)
        )
        ejeY = 98f
    }

    fun generarFooter(n: Int) {
        canvas.drawLine(MARGIN, PAGE_H - 20f, PAGE_W - MARGIN, PAGE_H - 20f, generarBorde(PDF_ACCENT_DK, 0.8f))
        canvas.drawText(
            "Página $n",
            PAGE_W / 2f, PAGE_H - 7f,
            generarTexto(9f, PDF_MUTED, align = Paint.Align.CENTER)
        )
    }

    fun saltoPagina() {
        generarFooter(numeroPagina)
        documento.finishPage(pagina)
        numeroPagina++
        pagina = documento.startPage(PdfDocument.PageInfo.Builder(PAGE_W, PAGE_H, numeroPagina).create())
        canvas = pagina.canvas
        generarRectangulo()
        generarHeader()
    }

    fun comprobarEspacio(needed: Float) {
        if (ejeY + needed > CONTENT_BOTTOM) saltoPagina()
    }

    fun seccionHeader(title: String) {
        comprobarEspacio(42f)
        ejeY += 4f
        canvas.drawRect(MARGIN, ejeY, MARGIN + 4f, ejeY + 22f, rellenarColor(PDF_ACCENT))
        canvas.drawText(title, MARGIN + 14f, ejeY + 16f, generarTexto(13f, PDF_ACCENT, bold = true))
        ejeY += 26f
        canvas.drawLine(MARGIN, ejeY, PAGE_W - MARGIN, ejeY, generarBorde(PDF_ACCENT_DK, 0.6f))
        ejeY += 10f
    }

    fun estadisticaFila(label: String, value: String) {
        comprobarEspacio(24f)
        canvas.drawText(label, MARGIN + 10f, ejeY + 14f, generarTexto(11f, PDF_MUTED))
        canvas.drawText(value, MARGIN + 145f, ejeY + 14f, generarTexto(11f, PDF_TEXT, bold = true))
        ejeY += 22f
    }

    fun filaPosiciones(resId: Int, rank: Int, name: String, detail: String) {
        val rowH = 60f
        comprobarEspacio(rowH + 8f)
        canvas.drawRoundRect(
            RectF(MARGIN + 4f, ejeY, PAGE_W - MARGIN - 4f, ejeY + rowH),
            8f, 8f, rellenarColor(PDF_ROW_BG)
        )

        val medalColor = when (rank) { 1 -> PDF_GOLD; 2 -> PDF_SILVER; else -> PDF_BRONZE }
        val cx = MARGIN + 22f
        val cy = ejeY + rowH / 2f
        canvas.drawCircle(cx, cy, 12f, rellenarColor(PDF_BLACK))
        canvas.drawCircle(cx, cy, 11f, rellenarColor(medalColor))
        canvas.drawText(
            "$rank",
            cx, cy + 4f,
            generarTexto(9f, PDF_BLACK, bold = true, align = Paint.Align.CENTER)
        )

        val bmp = BitmapFactory.decodeResource(context.resources, resId)
        val scaled = bmp.scale(44, 44, false)
        canvas.drawBitmap(scaled, MARGIN + 40f, ejeY + 8f, null)

        val tx = MARGIN + 94f
        canvas.drawText(name, tx, ejeY + 25f, generarTexto(12f, PDF_TEXT, bold = true))
        canvas.drawText(detail, tx, ejeY + 43f, generarTexto(10f, PDF_MUTED))

        ejeY += rowH + 6f
    }

    generarRectangulo()
    generarHeader()

    seccionHeader("Resultado de la aventura")

    comprobarEspacio(44f)
    val resultColor = if (datos.esVictoria) PDF_VICTORY else PDF_ACCENT
    val resultLabel = if (datos.esVictoria) "✓  VICTORIA" else "✕  DERROTA"
    canvas.drawRoundRect(RectF(MARGIN + 8f, ejeY, MARGIN + 110f, ejeY + 30f), 15f, 15f, rellenarColor(resultColor))
    canvas.drawText(
        resultLabel,
        MARGIN + 59f, ejeY + 20f,
        generarTexto(13f, PDF_WHITE, bold = true, align = Paint.Align.CENTER)
    )
    ejeY += 38f

    estadisticaFila("Vidas restantes", "${datos.vidas}")
    ejeY += 10f

    seccionHeader("Capturas")
    estadisticaFila("Pokémon capturados", "${datos.capturadas}")
    estadisticaFila("Pokémon perdidos",   "${datos.perdidas}")
    estadisticaFila("Total de rutas",     "${datos.totalRutas}")
    ejeY += 10f

    if (datos.topUsados.isNotEmpty()) {
        seccionHeader("Pokémon más usados en combate")
        datos.topUsados.take(3).forEachIndexed { i, e ->
            val mote = e.pokemon.mote?.takeIf { it.isNotBlank() }
            val nombre = if (mote != null && mote != e.especie.nombre) "$mote (${e.especie.nombre})" else e.especie.nombre
            filaPosiciones(e.especie.imagen, i + 1, nombre, "${e.veces} combates")
        }
        ejeY += 8f
    }

    if (datos.topCombatesMortales.isNotEmpty()) {
        seccionHeader("Combates más mortales")
        datos.topCombatesMortales.take(3).forEachIndexed { i, e ->
            filaPosiciones(e.combate.imagenRival, i + 1, e.combate.nombreRival, "${e.muertes} caídos en combate")
        }
        ejeY += 8f
    }

    if (datos.topAsesinos.isNotEmpty()) {
        seccionHeader("Rivales más letales")
        datos.topAsesinos.take(3).forEachIndexed { i, e ->
            filaPosiciones(e.especie.imagen, i + 1, e.especie.nombre, "${e.victimas} víctimas")
        }
        ejeY += 8f
    }

    if (datos.distribucionTipos.isNotEmpty()) {
        seccionHeader("Distribución de tipos")
        val maxVal = datos.distribucionTipos.values.maxOrNull()?.toFloat() ?: 1f
        val barZone = PAGE_W - MARGIN * 2 - 120f
        datos.distribucionTipos.entries
            .sortedByDescending { it.value }
            .forEach { (tipo, count) ->
                comprobarEspacio(24f)
                canvas.drawText(tipo.name, MARGIN + 8f, ejeY + 14f, generarTexto(10f, PDF_TEXT))
                val barW = (barZone * count / maxVal).coerceAtLeast(6f)
                canvas.drawRoundRect(
                    RectF(MARGIN + 100f, ejeY + 4f, MARGIN + 100f + barW, ejeY + 18f),
                    3f, 3f, rellenarColor(PDF_ACCENT_DK)
                )
                canvas.drawText(
                    "$count",
                    MARGIN + 106f + barW, ejeY + 14f,
                    generarTexto(9f, PDF_MUTED)
                )
                ejeY += 22f
            }
    }

    seccionHeader("Asistente IA NuzBot")
    estadisticaFila("Consultas realizadas", "${datos.consultasIA}")
    ejeY += 10f

    generarFooter(numeroPagina)
    documento.finishPage(pagina)
    guardarPDF(context, documento)
    documento.close()
}

private fun rellenarColor(color: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    this.color = color
    style = Paint.Style.FILL
}

private fun generarBorde(color: Int, width: Float) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    this.color = color
    style = Paint.Style.STROKE
    strokeWidth = width
}

private fun generarTexto(
    size: Float,
    color: Int,
    bold: Boolean = false,
    align: Paint.Align = Paint.Align.LEFT
) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    textSize = size
    this.color = color
    typeface = if (bold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD) else Typeface.DEFAULT
    textAlign = align
}

private fun guardarPDF(context: Context, doc: PdfDocument) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "resumen_aventura.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { os -> doc.writeTo(os) }
            Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_SHORT).show()
        }
    } else {
        @Suppress("DEPRECATION")
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "resumen_aventura.pdf"
        )
        FileOutputStream(file).use { os -> doc.writeTo(os) }
        Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_SHORT).show()
    }
}
