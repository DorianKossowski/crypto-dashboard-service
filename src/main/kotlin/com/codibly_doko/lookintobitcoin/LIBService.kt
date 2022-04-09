package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBBitcoinChartRequestDto
import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartRequestDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartRequestDto
import jakarta.inject.Singleton

@Singleton
class LIBService(private val client: LIBClient) {

    fun getZScoreChart() = client.fetchZScore(LIBZScoreChartRequestDto())

    fun getReserveRiskChart() = client.fetchReserveRisk(LIBReserveRiskChartRequestDto())

    fun getBitcoinChart() = client.fetchBitcoin(LIBBitcoinChartRequestDto())
}