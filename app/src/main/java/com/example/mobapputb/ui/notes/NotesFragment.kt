package com.example.mobapputb.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobapputb.MainActivity
import com.example.mobapputb.R
import com.example.mobapputb.databinding.FragmentNotesBinding
import com.example.mobapputb.viewAdapters.NoteDataAdapter

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel

    var buttonAddNote: Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivity = requireActivity() as MainActivity
        val repository = mainActivity.MyApp.noteRepository

        binding = FragmentNotesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NotesViewModel::class.java]

        // Set ViewModel in DataBinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Set up RecyclerView and adapter
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        val myAdapter = NoteDataAdapter(viewModel.noteAdapterData.value!!)
        binding.recyclerViewNoteData.adapter = myAdapter

        // Update the adapter with the new list
        viewModel.noteAdapterData.observe(viewLifecycleOwner, Observer { newList ->
            binding.recyclerViewNoteData.adapter = NoteDataAdapter(newList)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddNote = view.findViewById<Button>(R.id.buttonAddNote)
        buttonAddNote?.setOnClickListener{ findNavController().navigate(R.id.navigation_note_add) }

        viewModel.loadNoteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}