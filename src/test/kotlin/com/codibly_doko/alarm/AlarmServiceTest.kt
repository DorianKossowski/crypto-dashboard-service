package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.model.Alarm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.security.Principal
import java.time.Instant
import java.util.*

internal class AlarmServiceTest {

    companion object {
        private const val NAME = "SOME_NAME"
        private const val DESCRIPTION = "SOME_DESC"
        private const val VALUE = 1.2
        private val LAST_OCCURRED = Instant.ofEpochMilli(1652006444)
    }

    private val alarmRepository: AlarmRepository = mock()
    private val alarmService = AlarmService(alarmRepository)

    @Test
    internal fun shouldGetAlarms() {
        // given
        val principal: Principal = mock()
        val uuid = UUID.randomUUID()
        whenever(principal.name).thenReturn(uuid.toString())
        whenever(alarmRepository.findAllByUserId(uuid)).thenReturn(
            listOf(
                Alarm(
                    uuid,
                    mock(),
                    NAME,
                    DESCRIPTION,
                    VALUE,
                    LAST_OCCURRED
                )
            )
        )

        // when
        val alarms = alarmService.getAlarms(principal).alarms

        //then
        assertThat(alarms).hasSize(1)
        assertThat(alarms[0])
            .returns(uuid, AlarmDto::id)
            .returns(NAME, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)

    }
}