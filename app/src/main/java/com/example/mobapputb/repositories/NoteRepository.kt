package com.example.mobapputb.repositories

import android.util.Log
import com.example.mobapputb.databases.MyRoomDatabase
import com.example.mobapputb.databases.mapToDomain
import com.example.mobapputb.domains.NoteDomain
import com.example.mobapputb.domains.mapToDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NoteRepository(
    private val database: MyRoomDatabase
) {
    fun getNote(id: Int): Flow<NoteDomain> {
        return database.NoteDao.getNote(id).map { it.mapToDomain() }
    }

    fun getNotes(): Flow<List<NoteDomain>> {
        return database.NoteDao.getNotes().map { originalList -> originalList.map { it.mapToDomain()} }
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