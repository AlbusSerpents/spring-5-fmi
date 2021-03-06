package com.ddd.books.in.spring.func.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    public final ErrorCode code;
    public final String message;

    public enum ErrorCode {
        UNKNOWN_ERROR,
        REQUEST_VALIDATION_FAILED,
        AUTHENTICATION_REQUIRED,
        AUTHENTICATION_FAILED,
        REGISTRATION_FAILED,
        MISSING,
        OPERATION_FORBIDDEN,
        OPERATION_FAILED,
        PROFILE_UPDATE_FAILED,
        CLUB_NAME_ALREADY_TAKEN,
        DELETE_USER_FAILED,
        MEMBER_ALREADY_EXISTS,
        USER_NOT_A_MEMBER_OF_THE_CLUB,
        OWNER_CANNOT_LEAVE_CLUB,
        BOOK_ALREADY_EXISTS,
        BOX_NOT_FOUND,
        USER_ALREADY_VOTED,
        BOOK_NOT_IN_POLL,
        NO_POLLS_CREATED
    }
}
