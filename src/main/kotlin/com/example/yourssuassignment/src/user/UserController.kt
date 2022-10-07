package com.example.yourssuassignment.src.user

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.config.BaseResponseStatus
import com.example.yourssuassignment.src.user.model.CreateUserRes
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
    fun getUser(@PathVariable email: String): ResponseEntity<Any> {
        return try {
            val user: User? = userService.getUser(email)
            ResponseEntity.ok().body(user)
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @PostMapping("/sign-in")
    fun createUser(@RequestBody postUser: PostUser): ResponseEntity<Any> {
        val user = User(postUser.email, postUser.password, postUser.username)
        return try {
            userService.createUser(user)
            ResponseEntity.ok().body(CreateUserRes(postUser.email, postUser.password))
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @DeleteMapping("/delete")
    fun deleteUser(@RequestBody deleteUser: DeleteUser): ResponseEntity<Any> {
        return try {
            userService.deleteUser(deleteUser)
            ResponseEntity.ok().body(BaseResponse())
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }
}
