package com.example.yourssuassignment.src.article

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.config.BaseResponseStatus.*
import com.example.yourssuassignment.src.article.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController {
    @Autowired
    private lateinit var articleService: ArticleService

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): ResponseEntity<Any> {
        return try {
            val article: Article? = articleService.getArticle(articleId)
            ResponseEntity.ok().body(article)
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @PostMapping("/post")
    fun createUser(@RequestBody articleReq: CreateArticleReq): ResponseEntity<Any> {
        return try {
            val articleId: Long = articleService.createArticle(articleReq)
            ResponseEntity.ok().body(ArticleRes(articleId, articleReq))
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @DeleteMapping("/delete/{articleId}")
    fun deleteArticle(@RequestBody deleteArticleReq: DeleteArticleReq, @PathVariable articleId: Long): BaseResponse {
        return try {
            articleService.deleteArticle(DeleteArticle(deleteArticleReq.email, deleteArticleReq.password, articleId))
            BaseResponse()
        } catch (e: BaseException) {
            BaseResponse(e.status)
        }
    }

    @PutMapping("/update/{articleId}")
    fun updateArticle(@RequestBody updateArticleReq: UpdateArticleReq, @PathVariable articleId: Long): ResponseEntity<Any> {
        return try {
            articleService.updateArticle(UpdateArticle(updateArticleReq, articleId))
            ResponseEntity.ok().body(ArticleRes(articleId, updateArticleReq))
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }
}