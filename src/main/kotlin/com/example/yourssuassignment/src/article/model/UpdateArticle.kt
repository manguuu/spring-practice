package com.example.yourssuassignment.src.article.model

class UpdateArticle(
    val email: String,
    val password: String,
    val title: String,
    val content: String,
    val id: Long
) {
    constructor(req: UpdateArticleReq, articleId: Long):
            this(req.email, req.password, req.title, req.content, articleId)
}