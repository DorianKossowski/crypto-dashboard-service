package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmsResponseDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(value = "alarms")
class AlarmController(private val alarmService: AlarmService) {

    @Get
    fun getAlarms(principal: Principal): AlarmsResponseDto {
        return alarmService.getAlarms(principal)
    }
}