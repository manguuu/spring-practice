package com.example.yourssuassignment.src.article.model

import java.sql.Timestamp

data class Article(
    var articleId: Long?,
    var title: String,
    var content: String,
    val createdAt: Timestamp,
    var updatedAt: Timestamp,
    val userId: Long?
) {
    constructor(title: String, content: String, userId: Long?):
            this(null, title, content, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()), userId)
}