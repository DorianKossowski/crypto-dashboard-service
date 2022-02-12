package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartResponseDto
import jakarta.inject.Singleton

@Singleton
class LIBFacade(private val libService: LIBService) {

    fun getZScoreChart(): LIBZScoreChartResponseDto {
        return libService.getZScoreChart()
    }

    fun getReserveRiskChart(): LIBReserveRiskChartResponseDto {
        return libService.getReserveRiskChart()
    }
}