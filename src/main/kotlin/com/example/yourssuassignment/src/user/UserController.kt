package com.example.yourssuassignment.src.user

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.config.BaseResponseStatus
import com.example.yourssuassignment.src.user.model.DeleteUser
import com.example.yourssuassignment.src.user.model.PostUser
import com.example.yourssuassignment.src.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String): BaseResponse<Any?> {
        try {
            val user: User? = userService.getUser(email)
            return BaseResponse(user)
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
    }

    @PostMapping("/sign-in")
    fun createUser(@RequestBody postUser: PostUser): BaseResponse<Any> {
        val user = User(postUser.email, postUser.password, postUser.username)
        try {
            userService.createUser(user)
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("회원 가입 성공\n")
    }

    @DeleteMapping("/delete")
    fun deleteUser(@RequestBody deleteUser: DeleteUser): BaseResponse<Any> {
        try {
            userService.deleteUser(deleteUser)
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("회원 탈퇴 성공")
    }
}
