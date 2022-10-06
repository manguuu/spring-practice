package com.example.yourssuassignment.src.article

import com.example.yourssuassignment.src.article.model.Article
import com.example.yourssuassignment.src.article.model.UpdateArticle
import com.example.yourssuassignment.src.user.model.UserEmailPassword
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

    fun createArticle(article: Article): Int {
        val query = "insert into article(title, content, user_id, created_at, updated_at) value (?, ?, ?, ?, ?)"
        val params = arrayOf<Any?>(
            article.title,
            article.content,
            article.userId,
            article.createdAt,
            article.updatedAt
        )
        jdbcTemplate!!.update(query, *params)
        val lastInsertIdQuery = "select last_insert_id()"
        return jdbcTemplate!!.queryForObject(lastInsertIdQuery, Int::class.java)!!
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

    fun deleteArticle(id: Long): Int {
        val query = "delete from article where article_id = ?"
        return jdbcTemplate!!.update(query, id)
    }

    fun updateArticle(updateArticleReq: UpdateArticle): Int {
        val query = "update article set title=?, content=?, updated_at=? where article_id=?"
        return jdbcTemplate!!.update(query, updateArticleReq.title, updateArticleReq.content, Timestamp(System.currentTimeMillis()), updateArticleReq.id)
    }

    fun getUserEmailPassword(articleId: Long): UserEmailPassword? {
        val query = "select email, password from user join article a on user.user_id = a.user_id where article_id=?"
        try {
            return jdbcTemplate!!.queryForObject(
                query,
                RowMapper { rs: ResultSet, _: Int ->
                    UserEmailPassword(
                        rs.getString("email"),
                        rs.getString("password"),
                    )
                }, articleId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }
}