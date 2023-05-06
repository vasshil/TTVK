package com.app.ttvk.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ttvk.databinding.FileItemBinding
import com.app.ttvk.model.FileModel
import kotlin.math.log10
import kotlin.math.pow

class FileAdapter(private val files: List<FileModel>) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private lateinit var binding: FileItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        binding = FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.bind(file)
    }

    override fun getItemCount() = files.size


    class FileViewHolder(private val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: FileModel) {
            binding.icon.setImageResource(file.icon)
            binding.name.text = file.name
            binding.size.text = formatSize(file.size)
            binding.date.text = formatDate(file.date)
        }

        private fun formatSize(size: Long): String {
            if (size <= 0) return "0 б"
            val units = arrayOf("байт", "Кбайт", "Мбайт", "Гбайт", "Тбайт")
            val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
            return String.format("%.2f %s", size / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
        }


        private fun formatDate(date: Long): String {
            // Форматирование даты файла в человекочитаемый вид (например, "01.01.2022 12:34")
            return date.toString()
        }

    }

}
