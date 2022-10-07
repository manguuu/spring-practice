package com.example.yourssuassignment.config


/**
 * 에러 코드 관리
 */

//BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
enum class BaseResponseStatus
    (val isSuccess: Boolean, val code: Int, val message: String) {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // users/sign-up
    INVALID_EMAIL(false, 2101, "올바르지 않은 이메일입니다."),
    INVALID_PASSWORD(false, 2105, "올바르지 않은 비밀번호입니다."),

//    //comment
//    EMPTY_COMMENT(false, 2111, "댓글 내용을 입력해주세요."),

    EMPTY_CONTENT(false, 2112, "content 내용을 입력해주세요."),
    EMPTY_TITLE(false, 2113, "title 내용을 입력해주세요."),
    /**
     * 3000 : Response 오류
     */

    // users/sign-up
    DUPLICATED_EMAIL(false, 3100, "중복된 이메일입니다."),
    DUPLICATED_USERNAME(false, 3101, "중복된 닉네임입니다."),


    //user/sign-in
    NOT_EXISTS_USER(false, 3103, "일치하는 회원정보가 없습니다."),


    //users/:userId
    INVALID_USERID(false, 3111, "user 정보가 올바르지 않습니다."),

    NOT_EXISTS_ARTICLE(false, 3112, "일치하는 article 정보가 없습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");

    companion object {
        fun of(errorName: String?): BaseResponseStatus {
            return BaseResponseStatus.valueOf(errorName.toString())
        }
    }
}