package com.codibly_doko.chart

import com.codibly_doko.common.model.ChartType
import jakarta.inject.Singleton

@Singleton
open class ChartService(private val chartProviders: List<ChartProvider>) {

    open fun getChartDto(chartType: ChartType) = (chartProviders.find { it.canHandle(chartType) }
        ?: throw IllegalArgumentException("No chart provider for type $chartType")).getChart(chartType)
}