package com.codibly_doko.lookintobitcoin.dto

internal class LIBZScoreChartResponseDtoTest : LIBChartResponseDtoTest() {
    override fun getName() = "Z-Score"

    override fun deserializationFunction(): (String) -> LIBChartResponseDto {
        return { s -> objectMapper.readValue(s, LIBZScoreChartResponseDto::class.java) }
    }
}