package com.example.yourssuassignment.src.comment.model

import com.fasterxml.jackson.annotation.JsonProperty

class CommentRes(
    @JsonProperty("commentId") val commentId: Long,
    @JsonProperty("email") val email: String,
    @JsonProperty("content") val content: String
) {
    constructor(commentId: Long, commentReq: CommentReq):
            this(commentId, commentReq.email, commentReq.content)
}