package com.example.mobapputb.domains

import java.time.OffsetDateTime

data class NoteDomain(
    val id: Int,
    val timestamp: OffsetDateTime,
    val title: String,
    val description: String
)
