package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartRequestDto
import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartRequestDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartResponseDto
import jakarta.inject.Singleton

@Singleton
class LIBService(private val client: LIBClient) {

    fun getZScoreChart(): LIBZScoreChartResponseDto {
        return client.fetchZScore(LIBZScoreChartRequestDto())
    }

    fun getReserveRiskChart(): LIBReserveRiskChartResponseDto {
        return client.fetchReserveRisk(LIBReserveRiskChartRequestDto())
    }
}