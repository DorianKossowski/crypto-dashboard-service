package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBChartResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class LIBResponseDeserializerTest {

    private val deserializer = DummyDeserializer()

    @Test
    internal fun shouldCreateDto() {
        assertThat(deserializer.createDto("", listOf(), listOf())).isSameAs(DummyDeserializer.RESPONSE_DTO)
    }

    @Test
    internal fun shouldGetName() {
        assertThat(deserializer.getChartName()).isSameAs(DummyDeserializer.NAME)
    }

    private class DummyDeserializer : LIBResponseDeserializer(LIBChartResponseDto::class.java) {

        companion object {
            val RESPONSE_DTO: LIBChartResponseDto = mock()
            const val NAME = "Some-Name"
        }

        override fun createDto(name: String, dates: List<String>, values: List<Double>) = RESPONSE_DTO

        override fun getChartName() = NAME
    }
}
