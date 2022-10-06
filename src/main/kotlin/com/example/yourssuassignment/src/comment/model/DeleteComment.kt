package com.example.yourssuassignment.src.comment.model

class DeleteComment(
    val email: String,
    val password: String,
    val articleId: Long,
    val commentId: Long
) {
    constructor(deleteCommentReq: DeleteCommentReq, articleId: Long, commentId: Long):
            this(
                deleteCommentReq.email,
                deleteCommentReq.password,
                articleId,
                commentId
            )
}