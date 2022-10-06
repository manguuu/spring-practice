package com.example.yourssuassignment.src.comment

import com.example.yourssuassignment.config.BaseException
import com.example.yourssuassignment.config.BaseResponse
import com.example.yourssuassignment.src.article.model.UpdateArticle
import com.example.yourssuassignment.src.article.model.UpdateArticleReq
import com.example.yourssuassignment.src.comment.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController {
    @Autowired
    private lateinit var commentService: CommentService

    @PostMapping("post/{articleId}")
    fun createComment(@RequestBody createCommentReq: CommentReq, @PathVariable articleId: Long): BaseResponse<Any> {
        try {
            commentService.createComment(CreateComment(createCommentReq, articleId))
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("comment create 성공")
    }

    @PutMapping("/update/{articleId}/{commentId}")
    fun updateArticle(
        @RequestBody commentReq: CommentReq,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long
    ): BaseResponse<Any> {
        try {
            commentService.updateComment(UpdateComment(commentReq, articleId, commentId))
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("comment update 성공")
    }

    @DeleteMapping("/delete/{articleId}/{commentId}")
    fun deleteComment(
        @RequestBody deleteCommentReq: DeleteCommentReq,
        @PathVariable articleId: Long,
        @PathVariable commentId: Long
    ): BaseResponse<Any> {
        try {
            commentService.deleteComment(DeleteComment(deleteCommentReq, articleId, commentId))
        } catch (e: BaseException) {
            return BaseResponse(e.status)
        }
        return BaseResponse("comment delete 성공")
    }
}