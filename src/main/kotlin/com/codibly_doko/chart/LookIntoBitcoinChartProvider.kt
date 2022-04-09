package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import com.codibly_doko.chart.dto.mapper.ChartDtoMapper
import com.codibly_doko.lookintobitcoin.LIBFacade
import com.codibly_doko.lookintobitcoin.dto.LIBChartResponseDto
import io.micronaut.cache.annotation.Cacheable
import jakarta.inject.Singleton

@Singleton
open class LookIntoBitcoinChartProvider(private val lookIntoBitcoinFacade: LIBFacade) : ChartProvider {
    companion object {
        private val CHART_PROVIDERS = mapOf<ChartType, (LIBFacade) -> LIBChartResponseDto>(
            ChartType.MVRV_ZSCORE to { libFacade -> libFacade.getZScoreChart() },
            ChartType.RESERVE_RISK to { libFacade -> libFacade.getReserveRiskChart() },
            ChartType.BITCOIN to { libFacade -> libFacade.getBitcoinChart() },
        )
    }

    override fun canHandle(chartType: ChartType): Boolean = CHART_PROVIDERS.contains(chartType)

    @Cacheable("lib-charts")
    override fun getChart(chartType: ChartType): ChartDto {
        val zScoreChartResponseDto =
            (CHART_PROVIDERS[chartType] ?: throw IllegalArgumentException("$chartType is not supported"))
                .invoke(lookIntoBitcoinFacade)
        return ChartDtoMapper.INSTANCE.fromLIBChartResponseDto(zScoreChartResponseDto)
    }
}