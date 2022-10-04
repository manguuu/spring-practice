package com.example.yourssuassignment.src.user

import com.example.yourssuassignment.src.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.sql.DataSource


@Repository
class UserRepository {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    fun setDataSource(dataSource: DataSource?) {
        jdbcTemplate = JdbcTemplate(dataSource!!)
    }

    //닉네임 중복 검사
    fun checkUsername(username: String?): Int {
        val query = "select exists(select user_id from user where username = ?)"
        return jdbcTemplate!!.queryForObject(query, Int::class.java, username)
    }

    //이메일 중복 검사
    fun checkEmail(email: String?): Int {
        val query = "select exists(select user_id from user where email = ?)"
        return jdbcTemplate!!.queryForObject(query, Int::class.java, email)
    }

    //회원가입
    fun createUser(user: User): Int {
        val query = "insert into User(email, password, username, updated_at, created_at) value (?, ?, ?, ?, ?)"
        val params = arrayOf<Any>(
            user.email,
            user.password,
            user.username,
            user.updatedAt,
            user.createdAt
        )
        jdbcTemplate!!.update(query, *params)
        val lastInsertIdQuery = "select last_insert_id()"
        return jdbcTemplate!!.queryForObject(lastInsertIdQuery, Int::class.java)!!
    }


    //유저 정보 조회
    fun getUser(email: String): User? {
        val query = "select * from user where email = ?"
        try {
            return jdbcTemplate!!.queryForObject(
                query,
                RowMapper { rs: ResultSet, _: Int ->
                    User(
                        rs.getLong("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                    )
                }, email)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    //회원탈퇴
    fun deleteUser(email: String): Int {
        val query = "delete from user where email = ?"
        return jdbcTemplate!!.update(query, email)
    }
}