package com.example.mobapputb.databases

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("select * from notes WHERE id = :id")
    fun getNote(id: Int): Flow<NoteDTO>

    @Query("select * from notes")
    fun getNotes(): Flow<List<NoteDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteDTO)
}

@Database(entities = [NoteDTO::class], version = 2)
abstract class MyRoomDatabase: RoomDatabase() {
    abstract val NoteDao: NoteDao

    companion object {
        private const val NEW_VERSION = 2
    }
}

private lateinit var INSTANCE: MyRoomDatabase

fun getDatabase(context: Context): MyRoomDatabase {
    synchronized(MyRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MyRoomDatabase::class.java,
                "my_room_database")
                .fallbackToDestructiveMigration() // Recreate the database if schema changed
                .build()
        }
    }
    return INSTANCE
}