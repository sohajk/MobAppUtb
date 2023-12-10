package com.example.mobapputb.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobapputb.R

class NoteAddFragment : Fragment() {

    var buttonNotes: Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNotes = view.findViewById<Button>(R.id.buttonNotes)
        buttonNotes?.setOnClickListener{ findNavController().navigate(R.id.navigation_home) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}