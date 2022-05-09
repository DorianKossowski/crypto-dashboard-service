package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import com.codibly_doko.common.model.ChartType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class ChartServiceTest {
    companion object {
        private val CHART_TYPE = ChartType.MVRV_ZSCORE
    }

    private val chartProvider: ChartProvider = mock()
    private val chartService = ChartService(listOf(chartProvider))

    @Test
    internal fun shouldGetChartDto() {
        // given
        val chartDto: ChartDto = mock()
        whenever(chartProvider.canHandle(CHART_TYPE)).thenReturn(true)
        whenever(chartProvider.getChart(CHART_TYPE)).thenReturn(chartDto)

        // when then
        assertThat(chartService.getChartDto(CHART_TYPE)).isSameAs(chartDto)
    }

    @Test
    internal fun shouldThrow() {
        whenever(chartProvider.canHandle(CHART_TYPE)).thenReturn(false)

        assertThatThrownBy { chartService.getChartDto(CHART_TYPE) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("No chart provider for type MVRV_ZSCORE")
    }
}