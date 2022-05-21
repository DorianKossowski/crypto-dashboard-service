package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.dto.AlarmsResponseDto
import com.codibly_doko.alarm.exception.MissingResourceException
import com.codibly_doko.alarm.exception.UserAccessDeniedException
import com.codibly_doko.alarm.model.Alarm
import com.codibly_doko.alarm.model.mapper.AlarmMapper
import com.codibly_doko.common.model.ChartType
import com.codibly_doko.user.UserRepository
import com.codibly_doko.user.dto.mapper.AlarmDtoMapper
import jakarta.inject.Singleton
import java.security.Principal
import java.util.*

@Singleton
open class AlarmService(private val alarmRepository: AlarmRepository, private val userRepository: UserRepository) {

    open fun getAlarms(principal: Principal, chartType: ChartType): AlarmsResponseDto {
        val alarms = alarmRepository.findAllByUserIdAndChartType(UUID.fromString(principal.name), chartType)
        return AlarmsResponseDto(AlarmDtoMapper.INSTANCE.fromAlarms(alarms))
    }

    open fun addAlarm(principal: Principal, alarmDto: AlarmDto): AlarmDto {
        val user = userRepository.findById(UUID.fromString(principal.name))
            .orElseThrow { MissingResourceException(principal.name) }
        val savedAlarm = alarmRepository.save(AlarmMapper.INSTANCE.fromAlarmDto(alarmDto, user))
        return AlarmDtoMapper.INSTANCE.fromAlarm(savedAlarm)
    }

    open fun updateAlarm(principal: Principal, alarmDto: AlarmDto, id: String): AlarmDto {
        val alarm = fetchUserAlarm(id, principal.name)
        AlarmMapper.INSTANCE.updateAlarm(alarm, alarmDto)
        val savedAlarm = alarmRepository.update(alarm)
        return AlarmDtoMapper.INSTANCE.fromAlarm(savedAlarm)
    }

    open fun deleteAlarm(principal: Principal, id: String) {
        val alarm = fetchUserAlarm(id, principal.name)
        alarmRepository.delete(alarm)
    }

    private fun fetchUserAlarm(alarmId: String, userId: String): Alarm {
        val alarm = alarmRepository.findById(UUID.fromString(alarmId))
            .orElseThrow { MissingResourceException(alarmId) }
        if (alarm.user.id!!.equals(userId)) {
            throw UserAccessDeniedException()
        }
        return alarm
    }
}
