package com.codibly_doko.alarm.model.mapper

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.model.Alarm
import com.codibly_doko.user.model.User
import org.mapstruct.*
import org.mapstruct.factory.Mappers

@Mapper
interface AlarmMapper {
    companion object {
        val INSTANCE: AlarmMapper = Mappers.getMapper(AlarmMapper::class.java)
    }

    @Mapping(target = "id", ignore = true)
    fun fromAlarmDto(alarmDto: AlarmDto, user: User): Alarm

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateAlarm(@MappingTarget alarm: Alarm, alarmDto: AlarmDto)
}
