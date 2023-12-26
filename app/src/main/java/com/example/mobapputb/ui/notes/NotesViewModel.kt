package com.example.mobapputb.ui.notes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobapputb.domains.NoteDomain
import com.example.mobapputb.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val errorTitle = MutableLiveData<String>()
    val errorDescription = MutableLiveData<String>()
    val insertStatus = MutableLiveData<String>()

    fun insertNote() {
        var errorCount = 0;

        if (title.value.isNullOrEmpty()) {
            errorTitle.value = "Field `Title` is required"
            errorCount++;
        } else {
            errorTitle.value = ""
        }

        if (description.value.isNullOrEmpty()) {
            errorDescription.value = "Field `Description` is required"
            errorCount++;
        } else {
            errorDescription.value = ""
        }

        if (errorCount != 0) {
            return
        }

        val note = NoteDomain(id = null, timestamp = OffsetDateTime.now(), title = title.value!!, description = description.value!!)

        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertNote(note)
                insertStatus.postValue("Note inserted to database")
            }
        } catch (e: Exception) {
            Log.e("NoteError", e.message!!)
            insertStatus.postValue("Error when inserting note to database")
        }
    }
}
