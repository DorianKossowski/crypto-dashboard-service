package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.dto.AlarmsResponseDto
import com.codibly_doko.common.model.ChartType
import com.codibly_doko.user.UserRepository
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
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.security.Principal
import java.time.Instant
import java.util.*

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class AlarmControllerTest {

    companion object {
        private const val NAME = "SOME_NAME"
        private const val DESCRIPTION = "SOME_DESC"
        private const val VALUE = 1.2
        private val LAST_OCCURRED = Instant.ofEpochMilli(1652006444)
        private val CHART_TYPE = ChartType.BITCOIN
    }

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    @Inject
    private lateinit var alarmService: AlarmService

    @Inject
    private lateinit var objectMapper: ObjectMapper

    @Test
    @Order(0)
    internal fun shouldGetEmptyAlarms() {
        assertThat(fetchAlarms()).isEmpty()
    }

    @Test
    @Order(1)
    internal fun shouldAddAlarm() {
        // given
        val request = HttpRequest.POST(
            "/alarms",
            objectMapper.writeValueAsString(AlarmDto(null, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE))
        )
            .bearerAuth(getAccessToken())

        // when
        val body = client.toBlocking().retrieve(request)

        // then
        val alarm = objectMapper.readValue(body, AlarmDto::class.java)
        assertThat(alarm)
            .returns(NAME, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)
            .returns(CHART_TYPE, AlarmDto::chartType)
    }

    @Test
    @Order(2)
    internal fun shouldGetAlarms() {
        val alarms = fetchAlarms()
        assertThat(alarms).hasSize(1)
        assertThat(alarms[0])
            .returns(NAME, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)
    }

    @Test
    @Order(3)
    internal fun shouldUpdateAlarm() {
        // given
        val alarmId = fetchAlarmId()
        val newName = "new name"
        val request2 = HttpRequest.PUT(
            "/alarms/$alarmId",
            objectMapper.writeValueAsString(AlarmDto(null, newName, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE))
        )
            .bearerAuth(getAccessToken())

        // when
        val body2 = client.toBlocking().retrieve(request2)

        // then
        val alarm = objectMapper.readValue(body2, AlarmDto::class.java)
        assertThat(alarm)
            .returns(alarmId, AlarmDto::id)
            .returns(newName, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)
            .returns(CHART_TYPE, AlarmDto::chartType)
    }

    @Test
    @Order(4)
    internal fun shouldDeleteAlarm() {
        // given
        val alarmId = fetchAlarmId()
        val request = HttpRequest.DELETE<Unit>("/alarms/$alarmId")
            .bearerAuth(getAccessToken())

        // when
        client.toBlocking().exchange<Unit, Unit>(request)

        // then
        assertThat(fetchAlarms()).isEmpty()
    }

    private fun fetchAlarms(): List<AlarmDto> {
        val request = HttpRequest.GET<String>("/alarms?chartType=$CHART_TYPE")
            .bearerAuth(getAccessToken())

        val body = client.toBlocking().retrieve(request)

        return objectMapper.readValue(body, AlarmsResponseDto::class.java).alarms
    }

    private fun fetchAlarmId(): UUID? {
        val request = HttpRequest.GET<String>("/alarms?chartType=$CHART_TYPE")
            .bearerAuth(getAccessToken())

        val body = client.toBlocking().retrieve(request)

        return objectMapper.readValue(body, AlarmsResponseDto::class.java).alarms.first().id
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
    class MockAlarmService(
        alarmRepository: AlarmRepository,
        userRepository: UserRepository
    ) :
        AlarmService(alarmRepository, userRepository) {

        private val alarms: MutableMap<UUID, AlarmDto> = mutableMapOf()

        override fun getAlarms(principal: Principal, chartType: ChartType) =
            AlarmsResponseDto(
                alarms.values.toMutableList()
            )

        override fun addAlarm(principal: Principal, alarmDto: AlarmDto): AlarmDto {
            val id = UUID.randomUUID()
            alarms[id] = alarmDto.copy(id = id)
            return alarmDto
        }

        override fun updateAlarm(principal: Principal, alarmDto: AlarmDto, id: String): AlarmDto {
            val updatedAlarmDto = alarmDto.copy(id = UUID.fromString(id))
            alarms[UUID.fromString(id)] = updatedAlarmDto
            return updatedAlarmDto
        }

        override fun deleteAlarm(principal: Principal, id: String) {
            alarms.remove(UUID.fromString(id))
        }
    }
}