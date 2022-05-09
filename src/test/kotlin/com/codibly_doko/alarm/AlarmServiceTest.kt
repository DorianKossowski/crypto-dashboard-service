package com.codibly_doko.alarm

import com.codibly_doko.alarm.dto.AlarmDto
import com.codibly_doko.alarm.exception.MissingResourceException
import com.codibly_doko.alarm.model.Alarm
import com.codibly_doko.alarm.model.mapper.AlarmMapper
import com.codibly_doko.common.model.ChartType
import com.codibly_doko.user.UserRepository
import com.codibly_doko.user.dto.mapper.AlarmDtoMapper
import com.codibly_doko.user.model.User
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
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
        private val SOME_UUID = UUID.randomUUID()
        private val CHART_TYPE = ChartType.BITCOIN
    }

    private val alarmRepository: AlarmRepository = mock()
    private val userRepository: UserRepository = mock()
    private val alarmService = AlarmService(alarmRepository, userRepository)

    private val principal: Principal = mock()
    private val user: User = mock()

    @BeforeEach
    internal fun setUp() {
        whenever(principal.name).thenReturn(SOME_UUID.toString())
        whenever(user.id).thenReturn(SOME_UUID)
        whenever(userRepository.findById(SOME_UUID)).thenReturn(Optional.of(user))
    }

    @Test
    internal fun shouldGetAlarms() {
        // given
        whenever(alarmRepository.findAllByUserIdAndChartType(SOME_UUID, CHART_TYPE)).thenReturn(
            listOf(Alarm(SOME_UUID, mock(), NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE))
        )

        // when
        val alarms = alarmService.getAlarms(principal, CHART_TYPE).alarms

        //then
        assertThat(alarms).hasSize(1)
        assertThat(alarms[0])
            .returns(SOME_UUID, AlarmDto::id)
            .returns(NAME, AlarmDto::name)
            .returns(DESCRIPTION, AlarmDto::description)
            .returns(VALUE, AlarmDto::value)
            .returns(LAST_OCCURRED, AlarmDto::lastOccurred)
            .returns(CHART_TYPE, AlarmDto::chartType)
    }

    @Test
    internal fun shouldAddAlarm() {
        // given
        val alarmDto = AlarmDto(null, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        val savedAlarm = Alarm(UUID.randomUUID(), user, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        whenever(alarmRepository.save(AlarmMapper.INSTANCE.fromAlarmDto(alarmDto, user))).thenReturn(savedAlarm)

        // when
        val alarm = alarmService.addAlarm(principal, alarmDto)

        //then
        assertThat(alarm).isEqualTo(AlarmDtoMapper.INSTANCE.fromAlarm(savedAlarm))
    }

    @Test
    internal fun shouldUpdateAlarm() {
        // given
        val alarmId = UUID.randomUUID()
        val savedAlarm = Alarm(alarmId, user, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        whenever(alarmRepository.findById(alarmId)).thenReturn(Optional.of(savedAlarm))
        val newName = "new name"
        val alarmDto = AlarmDto(null, newName, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        val updatedAlarm = Alarm(alarmId, user, newName, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        whenever(alarmRepository.update(updatedAlarm)).thenReturn(updatedAlarm)

        // when
        val alarm = alarmService.updateAlarm(principal, alarmDto, alarmId.toString())

        //then
        assertThat(alarm).isEqualTo(AlarmDtoMapper.INSTANCE.fromAlarm(updatedAlarm))
    }

    @Test
    internal fun shouldThrowWhenMissingAlarm() {
        // given
        val alarmId = UUID.randomUUID()
        whenever(alarmRepository.existsById(alarmId)).thenReturn(false)
        val alarmDto = AlarmDto(alarmId, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)

        // when then
        assertThatThrownBy { alarmService.updateAlarm(principal, alarmDto, alarmId.toString()) }
            .isExactlyInstanceOf(MissingResourceException::class.java)
            .hasMessage("Missing resource with id $alarmId")
    }

    @Test
    internal fun shouldDeleteAlarm() {
        // given
        val alarmId = UUID.randomUUID()
        val alarm = Alarm(alarmId, user, NAME, DESCRIPTION, VALUE, LAST_OCCURRED, CHART_TYPE)
        whenever(alarmRepository.findById(alarmId)).thenReturn(Optional.of(alarm))

        // when
        alarmService.deleteAlarm(principal, alarmId.toString())

        //then
        verify(alarmRepository).delete(alarm)
    }
}