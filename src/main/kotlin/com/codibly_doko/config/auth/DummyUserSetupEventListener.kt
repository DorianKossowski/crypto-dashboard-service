package com.codibly_doko.config.auth

import com.codibly_doko.user.UserRepository
import com.codibly_doko.user.model.User
import io.micronaut.runtime.event.ApplicationStartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton


@Singleton
class DummyUserSetupEventListener(private val userRepository: UserRepository) {

    @EventListener
    fun onApplicationEvent(event: ApplicationStartupEvent?) {
        if (userRepository.findAll().none()) {
            userRepository.save(
                User(null, "dummy", "pass")
            )
        }
    }
}