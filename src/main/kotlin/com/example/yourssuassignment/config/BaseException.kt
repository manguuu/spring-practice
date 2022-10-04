package com.example.yourssuassignment.config

import org.springframework.web.bind.annotation.RestControllerAdvice

//BaseResponseStatus 객체에 매핑
class BaseException(status: BaseResponseStatus) : Exception() {
    public val status: BaseResponseStatus = status
}
