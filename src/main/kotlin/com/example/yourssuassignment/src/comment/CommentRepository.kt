package com.example.yourssuassignment.src.comment

import com.example.yourssuassignment.src.comment.model.Comment
import com.example.yourssuassignment.src.comment.model.DeleteComment
import com.example.yourssuassignment.src.comment.model.UpdateComment
import com.example.yourssuassignment.src.user.model.UserEmailPassword
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

    fun createComment(comment: Comment): Int {
        val query = "insert into comment(content, article_id, user_id, created_at, updated_at) value(?, ?, ?, ?, ?)"
        val params = arrayOf<Any?>(
            comment.content,
            comment.articleId,
            comment.userId,
            comment.createdAt,
            comment.updatedAt
        )
        jdbcTemplate!!.update(query, *params)
        val lastInsertIdQuery = "select last_insert_id()"
        return jdbcTemplate!!.queryForObject(lastInsertIdQuery, Int::class.java)!!
    }

    fun updateComment(updateComment: UpdateComment): Int {
        val query = "update comment set content=?, updated_at=? where comment_id=?"
        return jdbcTemplate!!.update(query, updateComment.content, Timestamp(System.currentTimeMillis()), updateComment.commentId)
    }

    fun deleteComment(id: Long): Int {
        val query = "delete from comment where comment_id=?"
        return jdbcTemplate!!.update(query, id)
    }
}