package kz.telecom.core.core_application.utils.extensions

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kz.telecom.core.core_application.data.constants.CoreConstant
import kz.telecom.core.core_application.data.constants.FileConstant
import kz.telecom.core.core_application.utils.os.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

fun Context?.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context?.hideKeyboard(view: View?) {
    try {
        val keyboard = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showKeyboard(view: View?) {
    view?.requestFocus()
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}


/**
 * Запускает вибрацию
 * @param [milliseconds] время вибрирование в миллисекундах
 */
fun Context.startVibrator(milliseconds: Long) {
    val v = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v?.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        v?.vibrate(milliseconds)
    }
}

fun Context.toSp(px: Float) = px / resources.displayMetrics.scaledDensity

fun Context.toPixel(dp: Float) =
    dp * resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

fun Context.tpDp(px: Float) =
    px / resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

/**
 * Показываем файл через стандартные средства устройства
 * @param uri файла
 * @param type файла например application/pdf
 */
fun Context.showFileInSystemDevice(uri: Uri, type: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, type)
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    startActivity(intent)
}

/**
 * Сохраняем файл по массиву байтов
 * @param byte массив байтов
 * @param fileName имя файла
 * @return абсолютный путь файла
 */
fun Context.saveFile(byte: ByteArray?, fileName: String): String {
    var file = File(CoreConstant.EMPTY)
    try {
        val appPath: String = filesDir?.absolutePath ?: CoreConstant.EMPTY
        file = File(appPath, fileName)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byte)
    } catch (e1: FileNotFoundException) {
        e1.printStackTrace()
    }

    return file.absolutePath
}


/**
 * @return язык устройства
 */
fun Context.getLocale(): String {
    return try {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }

        locale.language
    } catch (e: Exception) {
        e.printStackTrace()
        CoreConstant.EMPTY
    }
}


fun Context.getMimeType(uri: Uri): String {
    val file = File(getPath(uri).orEmpty())
    return getMimeType(file).orEmpty()
}

/**
 * Получить путь к файлу из URI. Это получит путь для доступа к хранилищу
 */
fun Context.getPath(uri: Uri): String? {

    val isKitKat =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
        if (isLocalStorageDocument(uri)) {
            return DocumentsContract.getDocumentId(uri)
        } else if (isExternalStorageDocument(
                uri
            )
        ) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory()
                    .toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                java.lang.Long.valueOf(id)
            )
            return getDataColumn(
                contentUri,
                null,
                null
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            when (type) {
                "image" -> {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                "video" -> {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }
                "audio" -> {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(
                split[1]
            )
            return getDataColumn(
                contentUri,
                selection,
                selectionArgs
            )
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            uri,
            null,
            null
        )
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

fun Context.getDataColumn(
    uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        cursor = contentResolver.query(
            uri ?: Uri.EMPTY, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return null
}

/**
 * Получение полного пути до файла
 */
fun Context.getFile(uri: Uri?): File {
    if (uri != null) {
        val path =
            getPath(uri)
        if (path != null && isLocal(path)) {
            return File(path)
        }
    }
    return File(CoreConstant.EMPTY)
}

/**
 * Попытка получить эскиз данного файла из MediaStore. это
 * не следует вызывать в потоке пользовательского интерфейса
 */
fun Context.getThumbnail(file: File): Bitmap? {
    return getThumbnail(
        getUri(file),
        getMimeType(file)
    )
}

/**
 * Попытка получить миниатюру данного Uri из MediaStore. это
 * не следует вызывать в потоке пользовательского интерфейса
 */
fun Context.getThumbnail(
    uri: Uri?,
    mimeType: String?
): Bitmap? {
    var bm: Bitmap? = null
    if (uri != null) {
        val resolver = contentResolver
        var cursor: Cursor? = null
        try {
            cursor = resolver.query(uri, null, null, null, null)
            val isMoveToList = cursor?.moveToFirst() ?: false
            if (isMoveToList) {
                val id = cursor?.getInt(0)
                if (mimeType?.contains("video") ?: false) {
                    bm = MediaStore.Video.Thumbnails.getThumbnail(
                        resolver,
                        id?.toLong() ?: 0,
                        MediaStore.Video.Thumbnails.MINI_KIND,
                        null
                    )
                } else if (mimeType?.contains(FileConstant.MIME_TYPE_IMAGE) ?: false) {
                    bm = MediaStore.Images.Thumbnails.getThumbnail(
                        resolver,
                        id?.toLong() ?: 0,
                        MediaStore.Images.Thumbnails.MINI_KIND,
                        null
                    )
                }
            }
        } catch (e: Exception) {
            // do nothing
        } finally {
            cursor?.close()
        }
    }
    return bm
}


/**
 * Проверка на существование файла в системе
 * @param fileName имя файла с расширением
 * @return true - файл существует, false - файл отсуствует и string путь файла
 */
fun Context.fileExist(fileName: String): Pair<Boolean, String> {
    return try {
        val appPath: String = filesDir?.absolutePath ?: CoreConstant.EMPTY
        val file = File(appPath, fileName)
        Pair(file.exists(), file.absolutePath)
    } catch (e: Exception) {
        Pair(false, CoreConstant.EMPTY)
    }
}

/**
 * Получение ширины экрана
 */
fun Context.withScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    return display.width
}

/**
 * Получение высоты экрана
 */
fun Context.heightScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    return display.height
}

/**
 * Получение высоты и ширины экрана
 */
fun Context.sizeScreen(): Pair<Int, Int> {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    return Pair(display.width, display.height)
}