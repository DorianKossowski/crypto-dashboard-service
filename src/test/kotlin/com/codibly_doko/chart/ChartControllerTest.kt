package com.codibly_doko.chart

import com.codibly_doko.chart.dto.ChartDto
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
internal class ChartControllerTest {

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    @Inject
    private lateinit var chartService: ChartService

    @Test
    internal fun shouldGetChart() {
        // given
        val request = HttpRequest.GET<String>("/charts/mvrv_zscore")

        // when
        val body = client.toBlocking().retrieve(request)

        // then
        assertThat(body).isEqualToIgnoringWhitespace(
            """
            {
                "dates": [ "MVRV_ZSCORE" ],
                "values": [ 1.0 ]
            }
        """.trimIndent()
        )
    }

    @Replaces(ChartService::class)
    @Singleton
    class MockChartService(chartProviders: List<ChartProvider>) : ChartService(chartProviders) {
        override fun getChartDto(chartType: ChartType) = ChartDto(listOf(chartType.name), listOf(1.0))
    }
}