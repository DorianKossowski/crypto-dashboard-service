package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto

interface ChartProvider {

    fun canHandle(chartType: ChartType): Boolean

    fun getChart(chartType: ChartType): ChartDto
}