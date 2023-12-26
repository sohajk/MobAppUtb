package com.example.mobapputb.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobapputb.MainActivity
import com.example.mobapputb.R
import com.example.mobapputb.databinding.FragmentNoteAddBinding

class NoteAddFragment : Fragment() {

    private lateinit var binding: FragmentNoteAddBinding
    private lateinit var viewModel: NotesViewModel

    var buttonNotes: Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivity = requireActivity() as MainActivity
        val repository = mainActivity.MyApp.noteRepository

        binding = FragmentNoteAddBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NotesViewModel::class.java]

        // Set ViewModel in DataBinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNotes = view.findViewById<Button>(R.id.buttonBackToNotes)
        buttonNotes?.setOnClickListener{ findNavController().navigate(R.id.navigation_home) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}