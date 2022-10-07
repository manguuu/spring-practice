package com.example.yourssuassignment.src.user.model

import com.fasterxml.jackson.annotation.JsonProperty

class CreateUserRes(
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String
) {
}