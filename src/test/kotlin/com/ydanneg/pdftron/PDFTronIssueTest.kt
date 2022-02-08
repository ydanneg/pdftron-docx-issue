package com.ydanneg.pdftron

import com.pdftron.filters.FilterWriter
import com.pdftron.filters.MemoryFilter
import com.pdftron.pdf.Convert
import com.pdftron.pdf.OfficeToPDFOptions
import com.pdftron.pdf.PDFDoc
import com.pdftron.pdf.PDFNet
import com.pdftron.sdf.SDFDoc
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class PDFTronIssueTest {

    @BeforeTest
    fun beforeTest() {
        PDFNet.initialize("demo:1641305844230:7b582c49030000000039f92b87c2e8a3016b22dae7a484b7dd13f3c186")
    }

    @AfterTest
    fun afterTest() {
        PDFNet.terminate()
    }

    @Test
    fun testDocxConversion() {
        val options = OfficeToPDFOptions().apply {
            templateParamsJson = """
                {"text1":"text value1","%signature1":"my special type value1"}
            """.trimIndent()
        }
        PDFDoc().apply {
            val templateInputStream = javaClass.getResourceAsStream("/template.docx")!!
            Convert.officeToPdf(this, templateInputStream.toMemoryFilter(), options)
            save(FileOutputStream("testout/converted.pdf"), SDFDoc.SaveMode.INCREMENTAL, null)
            close()
        }
    }


    private fun InputStream.toMemoryFilter(): MemoryFilter {
        val memoryFilter = MemoryFilter(available().toLong(), false) // false = sink
        val writer = FilterWriter(memoryFilter) // helper filter to allow us to write to buffer
        val bufferSize = 1024 * 1024 // set intermediate buffer to 1MiB
        val buffer = ByteArray(bufferSize)
        var read: Int
        while (read(buffer).also { read = it } != -1) {
            if (read < bufferSize) {
                // last read will (certainly) contain less bytes, so write just those
                for (i in 0 until read) {
                    writer.writeUChar(buffer[i])
                }
            } else {
                writer.writeBuffer(buffer)
            }
        }
        writer.flush() // Don't forget to flush!
        memoryFilter.setAsInputFilter() // switch from sink to source
        return memoryFilter
    }
}
