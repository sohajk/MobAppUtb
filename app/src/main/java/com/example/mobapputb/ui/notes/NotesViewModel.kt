package com.example.mobapputb.ui.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobapputb.domains.NoteDomain
import com.example.mobapputb.models.NoteDataAdapterModel
import com.example.mobapputb.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val errorTitle = MutableLiveData<String>()
    val errorDescription = MutableLiveData<String>()
    val insertStatus = MutableLiveData<String>()

    private val _noteDataAdapter = MutableLiveData<List<NoteDataAdapterModel>>().apply { value = emptyList<NoteDataAdapterModel>() }
    val noteAdapterData: LiveData<List<NoteDataAdapterModel>> get() = _noteDataAdapter

    fun loadNoteList() {
        _noteDataAdapter.value = emptyList<NoteDataAdapterModel>()

        try {
            viewModelScope.launch(Dispatchers.IO) {
                val notes = repository.getNotes().toList()
                val dataCount = notes.count()
                val dataList = _noteDataAdapter.value.orEmpty().toMutableList()
                val formatterTs = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

                for (i in 0 until dataCount step 1) {
                    val note = NoteDataAdapterModel(
                        id = notes[i].id,
                        timestamp = notes[i].timestamp.format(formatterTs),
                        title = notes[i].title,
                        description = notes[i].description
                    )

                    dataList.add(note)
                }

                _noteDataAdapter.postValue(dataList)
            }
        } catch (e: Exception) {
            Log.e("NoteError", e.message!!)
        }
    }

    fun deleteNote(noteId: Int) {
        val dataList = _noteDataAdapter.value.orEmpty().toMutableList()
        val noteToDelete = dataList[noteId]
        dataList.remove(noteToDelete)
        _noteDataAdapter.value = dataList

        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteNote(noteToDelete.id!!)
            }
        } catch (e: Exception) {
            Log.e("NoteError", e.message!!)
        }
    }

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
