package com.example.yourssuassignment.src.article.model

import com.fasterxml.jackson.annotation.JsonProperty

class ArticleRes(
    @JsonProperty("articleId") val articleId: Long,
    @JsonProperty("email") val email: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("content") val content: String
) {
    constructor(articleId: Long, articleReq: CreateArticleReq):
            this(
                articleId,
                articleReq.email,
                articleReq.title,
                articleReq.content
            )

    constructor(articleId: Long, articleReq: UpdateArticleReq):
            this(
                articleId,
                articleReq.email,
                articleReq.title,
                articleReq.content
            )
}