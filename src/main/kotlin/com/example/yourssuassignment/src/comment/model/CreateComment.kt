package com.example.yourssuassignment.src.comment.model

class CreateComment(
    val email: String,
    val password: String,
    val content: String,
    val articleId: Long
) {
    constructor(createCommentReq: CommentReq, articleId: Long):
            this(
                createCommentReq.email,
                createCommentReq.password,
                createCommentReq.content,
                articleId
            )
}