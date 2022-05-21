package com.codibly_doko.alarm.model

import com.codibly_doko.common.model.ChartType
import com.codibly_doko.user.model.User
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class Alarm(
    @Id @GeneratedValue var id: UUID?,
    @ManyToOne @JoinColumn(name = "user_id", nullable = false) val user: User,
    var name: String,
    var description: String?,
    var value: Double,
    var lastOccurred: Instant?,
    @Enumerated(EnumType.STRING) val chartType: ChartType
)
