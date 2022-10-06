package com.example.yourssuassignment.src.comment

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponseStatus.*
import com.example.yourssuassignment.src.article.ArticleRepository
import com.example.yourssuassignment.src.comment.model.Comment
import com.example.yourssuassignment.src.comment.model.CreateComment
import com.example.yourssuassignment.src.comment.model.DeleteComment
import com.example.yourssuassignment.src.comment.model.UpdateComment
import com.example.yourssuassignment.src.user.UserRepository
import com.example.yourssuassignment.src.user.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CommentService {
    fun createComment(createComment: CreateComment)
    fun updateComment(updateComment: UpdateComment)
    fun deleteComment(deleteComment: DeleteComment)
}

@Service
class CommentServiceImpl(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
): CommentService {

    private fun checkIsEmpty(text: String?): Boolean {
        if (text == "" || text == " " || text == null) return true
        return false
    }

    @Transactional
    override fun createComment(createComment: CreateComment) {
        if (checkIsEmpty(createComment.content)) throw BaseException(EMPTY_CONTENT)
        val userInfo = articleRepository.getUserEmailPassword(createComment.articleId)
            ?: throw BaseException(NOT_EXISTS_ARTICLE)
        if (userInfo.email != createComment.email) throw BaseException(INVALID_EMAIL)
        if (userInfo.password != createComment.password) throw BaseException(INVALID_PASSWORD)

        commentRepository.createComment(
            Comment(createComment.content, userInfo.id, createComment.articleId)
        )
    }

    override fun updateComment(updateComment: UpdateComment) {
        val user: User = userRepository.getUser(updateComment.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (user.password != updateComment.password) throw BaseException(INVALID_PASSWORD)
        if (checkIsEmpty(updateComment.content)) throw BaseException(EMPTY_CONTENT)

        if (commentRepository.updateComment(updateComment) == 0) throw BaseException(DATABASE_ERROR)
    }

    override fun deleteComment(deleteComment: DeleteComment) {
        val user: User = userRepository.getUser(deleteComment.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (articleRepository.getArticle(deleteComment.articleId) == null) {
            throw BaseException(NOT_EXISTS_ARTICLE)
        }
        if (user.password != deleteComment.password) {
            throw BaseException(INVALID_PASSWORD)
        }
        commentRepository.deleteComment(deleteComment.commentId)
    }

}