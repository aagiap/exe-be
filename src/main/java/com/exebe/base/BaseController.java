package com.exebe.base;


import com.exebe.exception.CustomException;
import com.exebe.exception.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected <T> BaseResponse<T> wrapSuccess(T data) {
        return BaseResponse.success(data);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<BaseResponse<Object>> handleNoResultException(NoResultException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.failure(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.failure(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(BaseResponse.failure(ex.getMessage(), ex.getCode()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleException(AuthorizationDeniedException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        BaseResponse.failure(
                                ex.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
        logger.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        BaseResponse.failure(
                                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
