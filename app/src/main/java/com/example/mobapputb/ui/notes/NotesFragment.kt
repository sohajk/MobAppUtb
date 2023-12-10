package com.example.mobapputb.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobapputb.R
import com.example.mobapputb.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel

    var buttonAddNote: Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        // Set ViewModel in DataBinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddNote = view.findViewById<Button>(R.id.buttonAddNote)
        buttonAddNote?.setOnClickListener{ findNavController().navigate(R.id.navigation_note_add) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}