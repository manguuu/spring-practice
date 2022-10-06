package com.example.yourssuassignment.src.comment.model

class UpdateComment(
    val email: String,
    val password: String,
    val content: String,
    val articleId: Long,
    val commentId: Long
) {
    constructor(commentReq: CommentReq, articleId: Long, commentId: Long):
            this(
                commentReq.email,
                commentReq.password,
                commentReq.content,
                articleId,
                commentId
            )
}