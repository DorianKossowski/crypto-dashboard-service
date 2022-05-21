package com.codibly_doko.lookintobitcoin.dto

class LIBBitcoinChartResponseDtoTest : LIBChartResponseDtoTest() {

    override fun getName() = "BTC Price"

    override fun deserializationFunction(): (String) -> LIBChartResponseDto {
        return { s -> objectMapper.readValue(s, LIBBitcoinChartResponseDto::class.java) }
    }
}
