package com.example.yourssuassignment.src.article

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponseStatus.*
import com.example.yourssuassignment.src.article.model.Article
import com.example.yourssuassignment.src.article.model.CreateArticleReq
import com.example.yourssuassignment.src.article.model.DeleteArticle
import com.example.yourssuassignment.src.article.model.UpdateArticle
import com.example.yourssuassignment.src.comment.CommentRepository
import com.example.yourssuassignment.src.user.UserRepository
import com.example.yourssuassignment.src.user.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface ArticleService {
    fun getArticle(articleId: Long): Article?
    fun createArticle(createArticleReq: CreateArticleReq): Long
    fun updateArticle(updateArticleReq: UpdateArticle)
    fun deleteArticle(deleteArticleReq: DeleteArticle)
}

@Service
class ArticleServiceImpl(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
    ): ArticleService {

    override fun getArticle(articleId: Long): Article? {
        return articleRepository.getArticle(articleId) ?: throw BaseException(NOT_EXISTS_ARTICLE)
    }

    fun checkIsEmpty(text: String?): Boolean {
        if (text == "" || text == " " || text == null) return true
        return false
    }

    @Transactional(rollbackFor = [BaseException::class])
    override fun createArticle(createArticleReq: CreateArticleReq): Long {
        val user: User = userRepository.getUser(createArticleReq.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (user.password != createArticleReq.password) throw BaseException(INVALID_PASSWORD)
        if (checkIsEmpty(createArticleReq.content)) throw BaseException(EMPTY_CONTENT)
        if (checkIsEmpty(createArticleReq.title)) throw BaseException(EMPTY_TITLE)

        return articleRepository.createArticle(
            Article(createArticleReq.title, createArticleReq.content, user.userId)
        )
    }

    @Transactional(rollbackFor = [BaseException::class])
    override fun updateArticle(updateArticleReq: UpdateArticle) {
        val user: User = userRepository.getUser(updateArticleReq.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (user.password != updateArticleReq.password) throw BaseException(INVALID_PASSWORD)
        if (checkIsEmpty(updateArticleReq.content)) throw BaseException(EMPTY_CONTENT)
        if (checkIsEmpty(updateArticleReq.title)) throw BaseException(EMPTY_TITLE)

        if (articleRepository.updateArticle(updateArticleReq) == 0) throw BaseException(DATABASE_ERROR)
    }

    @Transactional(rollbackFor = [BaseException::class])
    override fun deleteArticle(deleteArticleReq: DeleteArticle) {
        val user: User = userRepository.getUser(deleteArticleReq.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (articleRepository.getArticle(deleteArticleReq.articleId) == null) {
            throw BaseException(NOT_EXISTS_ARTICLE)
        }
        if (user.password != deleteArticleReq.password) {
            throw BaseException(INVALID_PASSWORD)
        }
        commentRepository.deleteCommentByArticleId(deleteArticleReq.articleId)
        articleRepository.deleteArticleByArticleId(deleteArticleReq.articleId)
    }

}