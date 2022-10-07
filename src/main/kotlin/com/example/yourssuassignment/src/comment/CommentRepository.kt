package com.example.yourssuassignment.src.comment

import com.example.yourssuassignment.src.comment.model.Comment
import com.example.yourssuassignment.src.comment.model.UpdateComment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import javax.sql.DataSource

@Repository
class CommentRepository {
    private var jdbcTemplate: JdbcTemplate? = null
    @Autowired
    fun setDataSource(dataSource: DataSource?) {
        jdbcTemplate = JdbcTemplate(dataSource!!)
    }

    fun createComment(comment: Comment): Long {
        val query = "insert into comment(content, article_id, user_id, created_at, updated_at) value(?, ?, ?, ?, ?)"
        val params = arrayOf<Any>(
            comment.content,
            comment.articleId,
            comment.userId,
            comment.createdAt,
            comment.updatedAt
        )
        jdbcTemplate!!.update(query, *params)
        val lastInsertIdQuery = "select last_insert_id()"
        return jdbcTemplate!!.queryForObject(lastInsertIdQuery, Long::class.java)!!
    }

    fun updateComment(updateComment: UpdateComment): Int {
        val query = "update comment set content=?, updated_at=? where comment_id=?"
        val params = arrayOf<Any>(
            updateComment.content,
            Timestamp(System.currentTimeMillis()),
            updateComment.commentId
        )
        return jdbcTemplate!!.update(query, *params)
    }

    fun deleteCommentByCommentId(id: Long): Int {
        val query = "delete from comment where comment_id=?"
        return jdbcTemplate!!.update(query, id)
    }

    fun deleteCommentByArticleId(articleId: Long): Int {
        val query = "delete from comment where article_id=?"
        return jdbcTemplate!!.update(query, articleId)
    }

    fun deleteCommentByUserId(userId: Long): Int {
        val query = "delete from comment where user_id=?"
        return jdbcTemplate!!.update(query, userId)
    }
}