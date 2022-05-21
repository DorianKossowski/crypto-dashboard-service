package com.codibly_doko.lookintobitcoin.dto

class LIBReserveRiskChartResponseDtoTest : LIBChartResponseDtoTest() {
    override fun getName() = "Reserve Risk"

    override fun deserializationFunction(): (String) -> LIBChartResponseDto {
        return { s -> objectMapper.readValue(s, LIBReserveRiskChartResponseDto::class.java) }
    }
}
