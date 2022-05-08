package com.codibly_doko.alarm

import com.codibly_doko.alarm.model.Alarm
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface AlarmRepository : CrudRepository<Alarm, UUID> {
    fun findAllByUserId(userId: UUID): List<Alarm>
}