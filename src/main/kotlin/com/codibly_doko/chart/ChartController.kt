package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Controller("charts")
class ChartController(private val chartService: ChartService) {

    @Get("/{type}")
    @ExecuteOn(TaskExecutors.IO)
    fun getChart(type: ChartType): ChartDto {
        return chartService.getChartDto(type)
    }
}