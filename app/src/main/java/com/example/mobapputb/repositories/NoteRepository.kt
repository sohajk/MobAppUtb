package com.example.mobapputb.repositories

import android.util.Log
import com.example.mobapputb.databases.MyRoomDatabase
import com.example.mobapputb.databases.mapToDomain
import com.example.mobapputb.domains.NoteDomain
import com.example.mobapputb.domains.mapToDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(
    private val database: MyRoomDatabase
) {
    suspend fun getNotes(): List<NoteDomain> {
        return withContext(Dispatchers.IO) {
            database.NoteDao.getNotes().map { it.mapToDomain()}
        }
    }

    suspend fun deleteNote(id: Int) {
        withContext(Dispatchers.IO) {
            database.NoteDao.deleteNote(id)
        }
    }


    suspend fun insertNote(note: NoteDomain) {
        withContext(Dispatchers.IO) {
            try {
                val noteDTO = note.mapToDatabase()
                database.NoteDao.insert(noteDTO)
            }catch (e: Exception) {
                Log.e("NoteError", e.message!!)
            }
        }
    }
}