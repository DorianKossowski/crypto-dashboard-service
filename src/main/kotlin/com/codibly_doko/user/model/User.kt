package com.codibly_doko.user.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(@Id @GeneratedValue var id: UUID?, @Column(unique = true) var mail: String, var password: String)
