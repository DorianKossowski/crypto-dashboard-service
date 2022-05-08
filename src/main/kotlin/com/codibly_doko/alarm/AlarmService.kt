package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmsResponseDto
import com.codibly_doko.user.dto.mapper.AlarmDtoMapper
import jakarta.inject.Singleton
import java.security.Principal
import java.util.*

@Singleton
open class AlarmService(private val alarmRepository: AlarmRepository) {

    open fun getAlarms(principal: Principal): AlarmsResponseDto {
        val alarms = alarmRepository.findAllByUserId(UUID.fromString(principal.name))
        return AlarmsResponseDto(AlarmDtoMapper.INSTANCE.fromAlarms(alarms))
    }
}