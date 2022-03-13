package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("charts")
class ChartController(private val chartService: ChartService) {

    @Get("/{type}")
    @ExecuteOn(TaskExecutors.IO)
    fun getChart(type: ChartType): ChartDto {
        return chartService.getChartDto(type)
    }
}