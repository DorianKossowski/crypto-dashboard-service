package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBBitcoinChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBReserveRiskChartResponseDto
import com.codibly_doko.lookintobitcoin.dto.LIBZScoreChartResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class LIBServiceTest {

    private val libClient: LIBClient = mock()
    private val libService = LIBService(libClient)

    @Test
    internal fun shouldGetZScoreChart() {
        val expected: LIBZScoreChartResponseDto = mock()
        whenever(libClient.fetchZScore(any())).thenReturn(expected)

        assertThat(libService.getZScoreChart()).isSameAs(expected)
    }

    @Test
    internal fun shouldGetReserveRiskChart() {
        val expected: LIBReserveRiskChartResponseDto = mock()
        whenever(libClient.fetchReserveRisk(any())).thenReturn(expected)

        assertThat(libService.getReserveRiskChart()).isSameAs(expected)
    }

    @Test
    internal fun shouldGetBitcoinChart() {
        val expected: LIBBitcoinChartResponseDto = mock()
        whenever(libClient.fetchBitcoin(any())).thenReturn(expected)

        assertThat(libService.getBitcoinChart()).isSameAs(expected)
    }
}