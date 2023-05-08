package com.app.ttvk.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ttvk.databinding.FileItemBinding
import com.app.ttvk.model.FileModel
import com.app.ttvk.utils.FileUtils

class FileAdapter() : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private lateinit var binding: FileItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        binding = FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = FileUtils.files[holder.adapterPosition]
        holder.bind(file)

        holder.binding.root.setOnClickListener {
            if (file.getFile().isDirectory) {
                FileUtils.selectCurrentDirectory(file.path)
                notifyDataSetChanged()
            }

        }

    }

    override fun getItemCount() = FileUtils.files.size


    class FileViewHolder(val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: FileModel) {
            binding.icon.setImageResource(FileUtils.getFileIcon(file.name))
            binding.name.text = file.name
            binding.date.text = FileUtils.formatDate(file.date)
            if (file.size == -1L) {
                binding.size.visibility = View.GONE
            } else {
                binding.size.text = FileUtils.formatSize(file.size)
            }

        }

    }

}
