package com.example.yourssuassignment.src.user

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.src.user.model.User
import org.springframework.stereotype.Service
import com.example.yourssuassignment.config.BaseResponseStatus.*
import com.example.yourssuassignment.src.user.model.DeleteUser
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun getUser(email: String): User?
    fun createUser(user: User)
    fun deleteUser(deleteUser: DeleteUser)
}

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {
    override fun getUser(email: String): User? {
        return userRepository.getUser(email)
    }

    override fun deleteUser(deleteUser: DeleteUser) {
        val user: User = getUser(deleteUser.email) ?: throw BaseException(NOT_EXISTS_USER)
        if (user.password != deleteUser.password) throw BaseException(INVALID_PASSWORD)
        userRepository.deleteUser(deleteUser.email)
    }

    @Transactional
    override fun createUser(user: User) {
        if (userRepository.checkEmail(user.email) == 1) {
            throw BaseException(DUPLICATED_EMAIL)
        }
        if (userRepository.checkUsername(user.username) == 1) {
            throw BaseException(DUPLICATED_USERNAME)
        }
        userRepository.createUser(user)
    }
}
