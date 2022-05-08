package com.codibly_doko.user.dto.mapper

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.model.Alarm
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface AlarmDtoMapper {
    companion object {
        val INSTANCE: AlarmDtoMapper = Mappers.getMapper(AlarmDtoMapper::class.java)
    }

    fun fromAlarms(alarm: List<Alarm>): List<AlarmDto>
}