package com.example.yourssuassignment.src.article

import com.example.yourssuassignment.src.article.model.Article
import com.example.yourssuassignment.src.article.model.UpdateArticle
import com.example.yourssuassignment.src.user.model.UserEmailPasswordId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Timestamp
import javax.sql.DataSource

@Repository
class ArticleRepository {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    fun setDataSource(dataSource: DataSource?) {
        jdbcTemplate = JdbcTemplate(dataSource!!)
    }

    fun createArticle(article: Article): Long {
        val query = "insert into article(title, content, user_id, created_at, updated_at) value (?, ?, ?, ?, ?)"
        val params = arrayOf<Any>(
            article.title,
            article.content,
            article.userId!!,
            article.createdAt,
            article.updatedAt
        )
        jdbcTemplate!!.update(query, *params)
        val lastInsertIdQuery = "select last_insert_id()"
        return jdbcTemplate!!.queryForObject(lastInsertIdQuery, Long::class.java)!!
    }

    fun getArticle(id: Long): Article? {
        val query = "select * from article where article_id = ?"
        try {
            return jdbcTemplate!!.queryForObject(
                query,
                RowMapper { rs: ResultSet, _: Int ->
                    Article(
                        rs.getLong("article_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getLong("user_id")
                    )
                }, id)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun deleteArticleByArticleId(articleId: Long): Int {
        val query = "delete from article where article_id=?"
        return jdbcTemplate!!.update(query, articleId)
    }

    fun deleteArticleByUserId(userId: Long): Int {
        val query = "delete from article where user_id=?"
        return jdbcTemplate!!.update(query, userId)
    }

    fun updateArticle(updateArticleReq: UpdateArticle): Int {
        val query = "update article set title=?, content=?, updated_at=? where article_id=?"
        val params = arrayOf<Any>(
            updateArticleReq.title,
            updateArticleReq.content,
            Timestamp(System.currentTimeMillis()),
            updateArticleReq.id
        )
        return jdbcTemplate!!.update(query, *params)
    }

    fun getUserEmailPassword(articleId: Long): UserEmailPasswordId? {
        val query = "select email, password, user.user_id from user join article a on user.user_id = a.user_id where article_id=?"
        try {
            return jdbcTemplate!!.queryForObject(
                query,
                RowMapper { rs: ResultSet, _: Int ->
                    UserEmailPasswordId(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getLong("user_id")
                    )
                }, articleId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }
}