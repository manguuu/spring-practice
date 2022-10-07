package com.example.yourssuassignment.src.user.model

import java.sql.Timestamp

data class User(
    val userId: Long?,
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