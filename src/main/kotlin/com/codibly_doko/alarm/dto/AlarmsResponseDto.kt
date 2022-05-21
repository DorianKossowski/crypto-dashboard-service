package com.codibly_doko.alarm.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class AlarmsResponseDto(
    @JsonInclude(JsonInclude.Include.ALWAYS) val alarms: List<AlarmDto>
)
