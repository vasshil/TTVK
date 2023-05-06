package com.app.ttvk.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ttvk.databinding.ActivityMainBinding
import com.app.ttvk.model.FileModel
import com.app.ttvk.recycler.FileAdapter
import com.app.ttvk.utils.FileUtils
import com.app.ttvk.utils.SortType
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var adapter: FileAdapter? = null

    private val fileUtils = FileUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

//        GlobalScope.launch {

        adapter = FileAdapter()
        val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        binding.filesRecycler.adapter = adapter
        binding.filesRecycler.layoutManager = linearLayoutManager

//        }

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