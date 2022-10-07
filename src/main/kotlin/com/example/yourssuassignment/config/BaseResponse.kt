package com.example.yourssuassignment.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.example.yourssuassignment.config.BaseResponseStatus.SUCCESS


class BaseResponse{
    //BaseResponse 객체를 사용할때 성공, 실패 경우
    @JsonProperty("isSuccess")
    private val isSuccess: Boolean

    @JsonProperty("message")
    private val message: String

    @JsonProperty("code")
    private val code: Int

    // 요청에 성공한 경우
    constructor() {
        this.isSuccess = SUCCESS.isSuccess
        this.message = SUCCESS.message
        this.code = SUCCESS.code
    }

    // 요청에 실패한 경우
    constructor(status: BaseResponseStatus) {
        this.isSuccess = status.isSuccess
        this.message = status.message
        this.code = status.code
    }
}