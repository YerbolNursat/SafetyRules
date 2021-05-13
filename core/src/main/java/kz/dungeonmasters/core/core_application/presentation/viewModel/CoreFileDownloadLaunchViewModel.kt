package kz.dungeonmasters.core.core_application.presentation.viewModel

import android.content.Context
import android.os.Environment
import kz.dungeonmasters.core.core_application.utils.delegates.CoreCoroutine
import java.io.File
import java.io.InputStream

abstract class CoreFileDownloadLaunchViewModel(vararg useCases: CoreCoroutine) :
    CoreLaunchViewModel(useCases = *useCases) {

    fun createFile(context: Context, fileName: String, fileExt: String): File? {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path
        val file = File("$storageDir/$fileName.$fileExt")
        return storageDir?.let { file }
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        outputFile.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

}

