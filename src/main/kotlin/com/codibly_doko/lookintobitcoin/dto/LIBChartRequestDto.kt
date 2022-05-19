package com.codibly_doko.lookintobitcoin.dto

abstract class LIBChartRequestDto(
    val output: String = "chart.figure",
    val inputs: List<LIBChartInputDto>
) {
    constructor(inputValue: String) : this(inputs = listOf(LIBChartInputDto(value = inputValue)))
}
