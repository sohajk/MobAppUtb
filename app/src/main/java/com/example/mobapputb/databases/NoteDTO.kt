package com.example.mobapputb.databases

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobapputb.domains.NoteDomain
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "notes")
data class NoteDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val timestamp: String,
    val title: String,
    val description: String
)

fun NoteDTO.mapToDomain(): NoteDomain {
    return NoteDomain(
        id = this.id,
        timestamp = OffsetDateTime.parse(this.timestamp, DateTimeFormatter.ISO_DATE_TIME),
        title = this.title,
        description = this.description
    )
}
