package com.example.mobapputb.domains

import com.example.mobapputb.databases.NoteDTO
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class NoteDomain(
    val id: Int?,
    val timestamp: OffsetDateTime,
    val title: String,
    val description: String
)

fun NoteDomain.mapToDatabase(): NoteDTO {
    return NoteDTO(
        timestamp = this.timestamp.format(DateTimeFormatter.ISO_DATE_TIME),
        title = this.title,
        description = this.description
    )
}
