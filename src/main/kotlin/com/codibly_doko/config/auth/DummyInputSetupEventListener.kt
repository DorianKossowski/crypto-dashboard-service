package com.codibly_doko.config.auth

import com.codibly_doko.alarm.AlarmRepository
import com.codibly_doko.alarm.model.Alarm
import com.codibly_doko.common.model.ChartType
import com.codibly_doko.user.UserRepository
import com.codibly_doko.user.model.User
import io.micronaut.runtime.event.ApplicationStartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import java.time.Instant


@Singleton
class DummyInputSetupEventListener(
    private val userRepository: UserRepository,
    private val alarmRepository: AlarmRepository
) {

    @EventListener
    fun onApplicationEvent(event: ApplicationStartupEvent?) {
        if (userRepository.findAll().none()) {
            val user = userRepository.save(
                User(null, "dummy", "pass")
            )
            alarmRepository.saveAll(
                listOf(
                    Alarm(null, user, "My Alarm1", null, 1.23, null, ChartType.MVRV_ZSCORE),
                    Alarm(null, user, "My Alarm2", "Some alarm", 4.56, Instant.now(), ChartType.RESERVE_RISK)
                )
            )
        }
    }
}