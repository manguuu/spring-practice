package com.example.yourssuassignment.src.comment.model

import java.sql.Timestamp

class Comment(
    val commentId: Long?,
    val createdAt: Timestamp,
    var updatedAt: Timestamp,
    var content: String,
    val userId: Long,
    val articleId: Long
) {
    constructor(content: String, userId: Long, articleId: Long):
            this(
                null,
                Timestamp(System.currentTimeMillis()),
                Timestamp(System.currentTimeMillis()),
                content, userId, articleId
            )
}