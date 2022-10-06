package com.example.yourssuassignment.src.article

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.config.BaseResponseStatus.*
import com.example.yourssuassignment.src.article.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController {
    @Autowired
    private lateinit var articleService: ArticleService

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): BaseResponse<Any?> {
        try {
            val article: Article? = articleService.getArticle(articleId)
            return BaseResponse(article)
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
    }

    @PostMapping("/post-article")
    fun createUser(@RequestBody articleReq: CreateArticleReq): BaseResponse<Any> {
        try {
            articleService.createArticle(articleReq)
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("article post 성공")
    }

    @DeleteMapping("/delete/{articleId}")
    fun deleteArticle(@RequestBody deleteArticleReq: DeleteArticleReq, @PathVariable articleId: Long): BaseResponse<Any> {
        try {
            articleService.deleteArticle(DeleteArticle(deleteArticleReq.email, deleteArticleReq.password, articleId))
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("article delete 성공")
    }

    @PutMapping("/update/{articleId}")
    fun updateArticle(@RequestBody updateArticleReq: UpdateArticleReq, @PathVariable articleId: Long): BaseResponse<Any> {
        try {
            articleService.updateArticle(UpdateArticle(updateArticleReq, articleId))
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("article update 성공")
    }
}