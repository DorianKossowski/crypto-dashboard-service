package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBBitcoinChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBChartRequestDto
import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartResponseDto
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${lookIntoBitcoin.url}")
interface LIBClient {

    @Post("/django_plotly_dash/app/mvrv_zscore/_dash-update-component")
    fun fetchZScore(@Body requestDto: LIBChartRequestDto): LIBZScoreChartResponseDto

    @Post("/django_plotly_dash/app/reserve_risk/_dash-update-component")
    fun fetchReserveRisk(@Body requestDto: LIBChartRequestDto): LIBReserveRiskChartResponseDto

    @Post("/django_plotly_dash/app/reserve_risk/_dash-update-component")
    fun fetchBitcoin(@Body requestDto: LIBChartRequestDto): LIBBitcoinChartResponseDto
}
