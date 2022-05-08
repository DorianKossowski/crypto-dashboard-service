package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.dto.AlarmsResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.security.Principal
import java.time.Instant
import java.util.*

@MicronautTest
internal class AlarmControllerTest(private val objectMapper: ObjectMapper) {

    companion object {
        private const val NAME = "SOME_NAME"
        private const val DESCRIPTION = "SOME_DESC"
        private const val VALUE = 1.2
        private val LAST_OCCURRED = Instant.ofEpochMilli(1652006444)
    }

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    @Inject
    private lateinit var alarmService: AlarmService

    @Test
    internal fun shouldGetAlarms() {
        // given
        val request = HttpRequest.GET<String>("/alarms")
            .bearerAuth(getAccessToken())

        // when
        val body = client.toBlocking().retrieve(request)

        // then
        val alarms = objectMapper.readValue(body, AlarmsResponseDto::class.java).alarms
        assertThat(alarms).hasSize(1)
        assertThat(alarms[0])
            .returns(NAME, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)
    }

    private fun getAccessToken(): String {
        val credentials = UsernamePasswordCredentials("dummy", "pass")
        val request: HttpRequest<*> = HttpRequest.POST("/login", credentials)
        val rsp: HttpResponse<BearerAccessRefreshToken> =
            client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        return rsp.body()!!.accessToken
    }

    @Replaces(AlarmService::class)
    @Singleton
    class MockAlarmService(alarmRepository: AlarmRepository) : AlarmService(alarmRepository) {
        override fun getAlarms(principal: Principal) =
            AlarmsResponseDto(
                listOf(
                    AlarmDto(
                        UUID.randomUUID(),
                        NAME,
                        DESCRIPTION,
                        VALUE,
                        LAST_OCCURRED
                    )
                )
            )
    }
}