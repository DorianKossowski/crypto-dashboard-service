package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import com.codibly_doko.common.model.ChartType
import com.codibly_doko.lookintobitcoin.LIBFacade
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LookIntoBitcoinChartProviderTest {

    private val libFacade: LIBFacade = mock()
    private val lookIntoBitcoinChartProvider = LookIntoBitcoinChartProvider(libFacade)

    @Test
    internal fun shouldProvideChart() {
        val responseDto = LIBZScoreChartResponseDto("", listOf("date"), listOf(1.0))
        whenever(libFacade.getZScoreChart()).thenReturn(responseDto)

        val chart = lookIntoBitcoinChartProvider.getChart(ChartType.MVRV_ZSCORE)

        assertThat(chart)
            .returns(listOf("date"), ChartDto::dates)
            .returns(listOf(1.0), ChartDto::values)
    }

    @ParameterizedTest
    @EnumSource(value = ChartType::class)
    fun shouldHandle(chartType: ChartType) {
        assertThat(lookIntoBitcoinChartProvider.canHandle(chartType)).isTrue
    }
}
