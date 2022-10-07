package com.example.yourssuassignment.src.comment

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.src.article.model.UpdateArticle
import com.example.yourssuassignment.src.article.model.UpdateArticleReq
import com.example.yourssuassignment.src.comment.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController {
    @Autowired
    private lateinit var commentService: CommentService

    @PostMapping("post/{articleId}")
    fun createComment(@RequestBody commentReq: CommentReq, @PathVariable articleId: Long): ResponseEntity<Any> {
        return try {
            val commentId = commentService.createComment(CreateComment(commentReq, articleId))
            ResponseEntity.ok().body(CommentRes(commentId, commentReq))
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @PutMapping("/update")
    fun updateArticle(
        @RequestBody commentReq: CommentReq,
        @RequestParam articleId: Long,
        @RequestParam commentId: Long
    ): ResponseEntity<Any> {
        return try {
            commentService.updateComment(UpdateComment(commentReq, articleId, commentId))
            ResponseEntity.ok().body(CommentRes(commentId, commentReq))
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }

    @DeleteMapping("/delete")
    fun deleteComment(
        @RequestBody deleteCommentReq: DeleteCommentReq,
        @RequestParam articleId: Long,
        @RequestParam commentId: Long
    ): ResponseEntity<Any> {
        return try {
            commentService.deleteComment(DeleteComment(deleteCommentReq, articleId, commentId))
            ResponseEntity.ok().body(BaseResponse())
        } catch (e: BaseException) {
            ResponseEntity.badRequest().body(BaseResponse(e.status))
        }
    }
}