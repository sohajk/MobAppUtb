package com.example.mobapputb.viewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobapputb.databinding.ItemNoteDataBinding
import com.example.mobapputb.models.NoteDataAdapterModel

interface NoteRecyclerViewListener{
    fun onNoteDeleteClick(position: Int)
}

class NoteDataAdapter(private val dataList: List<NoteDataAdapterModel>, private val listener: NoteRecyclerViewListener) : RecyclerView.Adapter<NoteDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteDataBinding .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.idTextView.text = data.id.toString()
        holder.timestampTextView.text = data.timestamp
        holder.titleTextView.text = data.title
        holder.descriptionTextView.text = data.description

        holder.trashLinearLayout.setOnClickListener {
            listener.onNoteDeleteClick(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(binding: ItemNoteDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val idTextView: TextView = binding.labelId
        val timestampTextView: TextView = binding.labelTimestamp
        val titleTextView: TextView = binding.labelTitle
        val descriptionTextView: TextView = binding.labelDescription
        val trashLinearLayout: LinearLayout = binding.layoutIconTrash
    }
}