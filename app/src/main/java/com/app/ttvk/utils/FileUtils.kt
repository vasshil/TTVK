package com.app.ttvk.utils

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import com.app.ttvk.R
import com.app.ttvk.model.FileModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

object FileUtils {

    private var currentDirectory: File = Environment.getExternalStorageDirectory()

    var files: MutableList<FileModel>

    var sortType = SortType.BY_NAME

    init {

        files = getFiles(currentDirectory)

        sortFiles(sortType)


    }

    private fun getFiles(directory: File): MutableList<FileModel> {
        val fileModels = mutableListOf<FileModel>()

        val currentFiles = directory.listFiles()
        if (currentFiles != null) {
            for (file in currentFiles) {
                val fileSize = if (file.isDirectory) -1L else file.length()
                fileModels.add(FileModel(file.name, fileSize, file.lastModified(), file.absolutePath))
                Log.d("file", fileModels[fileModels.lastIndex].toString())
            }
        }

        return fileModels
    }

    private fun sortFiles(by: SortType) {
        when(by) {
            SortType.BY_NAME -> files.sortBy {
                it.name
            }
            SortType.BY_SIZE -> files.sortBy {
                it.size
            }
            SortType.BY_DATE -> files.sortBy {
                it.date
            }
        }
    }

    fun selectCurrentDirectory(path: String) {
        currentDirectory = File(path + "/")

        files = getFiles(currentDirectory)
        sortFiles(sortType)

    }


    fun formatSize(size: Long): String {
        if (size == 0L) return "0 б"

        val units = arrayOf("байт", "Кбайт", "Мбайт", "Гбайт", "Тбайт")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return String.format("%.2f %s", size / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
    }


    fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date(date))
    }

    fun getFileIcon(fileName: String): Int {
        val extension = fileName.substringAfterLast(".", "")
        return when (extension.lowercase(Locale.getDefault())) {
            "jpg", "jpeg", "gif", "png" -> R.drawable.ic_file_image
            "doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "txt" -> R.drawable.ic_file_doc
            "mp3", "wav", "ogg", "midi", "m4a" -> R.drawable.ic_file_audio
            "mp4", "avi", "mkv", "mov", "flv", "wmv" -> R.drawable.ic_file_video
            "" -> R.drawable.ic_foldier
            else -> R.drawable.ic_file_other
        }
    }

}