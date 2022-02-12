package com.codibly_doko.chart

import jakarta.inject.Singleton

@Singleton
open class ChartService(private val chartProviders: List<ChartProvider>) {

    open fun getChartDto(chartType: ChartType) = chartProviders.stream()
        .filter { it.canHandle(chartType) }
        .findFirst()
        .orElseThrow { IllegalArgumentException("No chart provider for type $chartType") }
        .getChart(chartType)
}