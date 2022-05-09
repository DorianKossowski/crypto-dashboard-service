package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import com.codibly_doko.common.model.ChartType

interface ChartProvider {

    fun canHandle(chartType: ChartType): Boolean

    fun getChart(chartType: ChartType): ChartDto
}