package com.codibly_doko.lookintobitcoin.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LIBZScoreChartResponseDtoTest {

    private val objectMapper = ObjectMapper()

    @Test
    internal fun shouldDeserialize() {
        val input = """
            {
                "response": {
                    "props": {
                        "figure": {
                            "data": [
                                {
                                    "name": "Some", "x": [ "a" ], "y": [ 1 ]
                                },
                                {
                                    "name": "Z-Score", "x": [ "b" ], "y": [ 2 ]
                                }
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        val dto = objectMapper.readValue(input, LIBZScoreChartResponseDto::class.java)

        assertThat(dto)
            .returns("Z-Score", LIBZScoreChartResponseDto::name)
            .returns(listOf("b"), LIBZScoreChartResponseDto::dates)
            .returns(listOf(2.0), LIBZScoreChartResponseDto::values);
    }
}