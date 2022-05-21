package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.dto.AlarmsResponseDto
import com.codibly_doko.common.model.ChartType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(value = "alarms")
class AlarmController(private val alarmService: AlarmService) {

    @Get
    fun getAlarms(principal: Principal, @QueryValue chartType: ChartType): AlarmsResponseDto {
        return alarmService.getAlarms(principal, chartType)
    }

    @Post
    fun addAlarm(principal: Principal, @Body alarmDto: AlarmDto): AlarmDto {
        return alarmService.addAlarm(principal, alarmDto)
    }

    @Put("/{id}")
    fun updateAlarm(principal: Principal, @Body alarmDto: AlarmDto, id: String): AlarmDto {
        return alarmService.updateAlarm(principal, alarmDto, id)
    }

    @Delete("/{id}")
    fun deleteAlarm(principal: Principal, id: String) {
        alarmService.deleteAlarm(principal, id)
    }
}
