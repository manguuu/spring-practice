package com.example.yourssuassignment.src.user.model

import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class User(
    val Id: Long?,
    var email: String,
    var password: String,
    var username: String,
    val createdAt: Timestamp,
    var updatedAt: Timestamp
) {
    constructor(email: String, password: String, username: String):
            this(null, email, password, username, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()),
    )
}