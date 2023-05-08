package com.app.ttvk.model

import java.io.File

data class FileModel(
    val name: String,
    val size: Long,
    val date: Long,
    val path: String,
) {
    fun getFile() = File(path)
}
