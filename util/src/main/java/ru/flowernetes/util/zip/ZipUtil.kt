package ru.flowernetes.util.zip

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


object ZipUtil {

    fun unzip(inputStream: InputStream, destDir: File) {
        val buffer = ByteArray(1024 * 8)
        val zis = ZipInputStream(inputStream)

        var zipEntry = zis.nextEntry

        while (zipEntry != null) {
            val newFile = newFile(destDir, zipEntry)
            val fos = FileOutputStream(newFile)
            var len: Int

            while (zis.read(buffer).also { len = it } > 0) {
                fos.write(buffer, 0, len)
            }
            fos.close()
            zipEntry = zis.nextEntry
        }
        zis.closeEntry()
        zis.close()
    }

    private fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
        val destFile = File(destinationDir, zipEntry.name)
        val destDirPath = destinationDir.canonicalPath
        val destFilePath = destFile.canonicalPath

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of the target dir: " + zipEntry.name)
        }
        return destFile
    }
}