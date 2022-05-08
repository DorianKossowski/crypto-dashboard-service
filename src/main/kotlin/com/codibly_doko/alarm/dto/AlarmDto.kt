package com.codibly_doko.alarm.dto

import java.time.Instant
import java.util.*

data class AlarmDto(
    val id: UUID,
    val name: String,
    val description: String?,
    val value: Double,
    val lastOccurred: Instant?
)
