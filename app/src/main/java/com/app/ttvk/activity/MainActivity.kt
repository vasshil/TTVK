package com.app.ttvk.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ttvk.R
import com.app.ttvk.databinding.ActivityMainBinding
import com.app.ttvk.model.FileModel
import com.app.ttvk.recycler.FileAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: FileAdapter

    private lateinit var files: MutableList<FileModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

//        GlobalScope.launch {

            files = getFiles()
            adapter = FileAdapter(files)
            val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

            binding.filesRecycler.adapter = adapter
            binding.filesRecycler.layoutManager = linearLayoutManager

//        }

    }

    private fun getFiles(): MutableList<FileModel> {
        val files = mutableListOf<FileModel>()


        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATA
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}!=${MediaStore.Files.FileColumns.MEDIA_TYPE_NONE}"


        val cursor = contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )

        cursor.use {

            if (true == it?.moveToFirst()) {

                val nameColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
                val dateColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)
                val pathColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)

                do {
                    val name = it.getStringOrNull(nameColumn) ?: continue
                    val size = it.getLongOrNull(sizeColumn) ?: continue
                    val date = it.getLongOrNull(dateColumn) ?: continue
                    val path = it.getStringOrNull(pathColumn) ?: continue

                    Log.d("file", "getFiles: $name")

                    files.add(FileModel(name, size, date, path, getFileIcon(name)))

                } while (it.moveToNext())

            }

        }



//        cursor?.use {
//
//            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
//            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
//            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
//            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
//
//            while (it.moveToNext()) {
//                val name = it.getString(nameColumn)
//                val size = it.getLong(sizeColumn)
//                val date = it.getLong(dateColumn)
//                val path = it.getString(pathColumn)
//
//                Log.d("file", "getFiles: $name")
//
//                files.add(FileModel(name, size, date, path, getFileIcon(name)))
//            }
//        }

        return files
    }

    private fun getFileIcon(name: String): Int {
        return R.drawable.ic_file
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortFilesByName() {
        files.sortBy { it.name }
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortFilesBySize() {
        files.sortBy { it.size }
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortFilesByDate() {
        files.sortBy { it.date }
        adapter.notifyDataSetChanged()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(READ_EXTERNAL_STORAGE),
                1);
        }

    }

//
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()) {
//                        // Permission Granted for android 11+
//                    } else {
//                        showAndroid11PlusPermissionDialog()
//                    }
//                } else {
//                    // Permission Granted for android marshmallow+
//                }
//            } else {
//                // Permission not granted
//            }
//        }
//
//    private val android11PlusSettingResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (Environment.isExternalStorageManager()) {
//                // Permission Granted for android 11+
//            } else {
//                // Permission not granted
//            }
//        }
//
//    fun requestReadExternalStoragePermission() {
//        when {
//            checkPermission(READ_EXTERNAL_STORAGE) -> {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()) {
//                        // Permission Granted for android 11+
//                    } else {
//                        showAndroid11PlusPermissionDialog()
//                    }
//                } else {
//                    // Permission Granted
//                }
//            }
//            shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()) {
//                        // Permission Granted
//                        return
//                    }
//                }
//                // show rational dialog
//            }
//            else -> {
//                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
//                    requestAndroid10PermissionLauncher.launch(
//                        arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        )
//                    )
//                } else {
//                    requestPermissionLauncher.launch(
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                    )
//                }
//            }
//        }
//    }
//
//    private fun showAndroid11PlusPermissionDialog() {
//        MaterialAlertDialogBuilder(fragment.requireContext())
//            .setTitle(fragment.getString(R.string.allow_access))
//            .setMessage(fragment.getString(R.string.allow_access_detail))
//            .setPositiveButton(fragment.getString(R.string.open_setting)) { dialog, _ ->
//                val intent = Intent().apply {
//                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
//                    data = Uri.fromParts("package", fragment.requireContext().packageName, null)
//                }
//                dialog.dismiss()
//                android11PlusSettingResultLauncher.launch(intent)
//            }
//            .setNegativeButton(fragment.getString(R.string.not_now)) { dialog, _ ->
//                dialog.dismiss()
//            }
//            .create()
//            .show()
//    }
//
//    private fun checkPermission(permission: String) =
//        ContextCompat.checkSelfPermission(
//            fragment.requireContext(),
//            permission
//        ) == PackageManager.PERMISSION_GRANTED

}