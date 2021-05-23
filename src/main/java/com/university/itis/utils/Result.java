package com.university.itis.utils;

import com.university.itis.exceptions.ResultNotCheckedException;
import org.springframework.http.ResponseEntity;

public class Result<T> {
    private enum ResultEnum {
        SUCCESS, ERROR
    }

    private final T result;
    private final ErrorEntity error;
    private final ResultEnum resultEnum;

    // MARK: - Init

    private Result(T result) {
        this.result = result;
        this.error = null;
        this.resultEnum = ResultEnum.SUCCESS;
    }

    private Result(ErrorEntity error) {
        this.result = null;
        this.error = error;
        this.resultEnum = ResultEnum.ERROR;
    }

    private Result() {
        this.result = null;
        this.error = null;
        this.resultEnum = ResultEnum.SUCCESS;
    }

    // MARK: - Static

    public static Result success() { return new Result(); }
    public static <S>Result success(S value) { return new Result<S>(value); }
    public static Result error(ErrorEntity error) { return new Result(error); }

    // MARK: - Public methods

    public boolean isSuccess() {
        return resultEnum == ResultEnum.SUCCESS;
    }

    public boolean isError() {
        return resultEnum == ResultEnum.ERROR;
    }

    public T getResult() {
        if (result == null) {
            throw new ResultNotCheckedException("Result not checked");
        }
        return result;
    }

    public ErrorEntity getError() {
        if (error == null) {
            throw new ResultNotCheckedException("Result not checked");
        }
        return error;
    }

    // MARK: - Spring stuff

    public ResponseEntity getResponseEntity() {
        switch (resultEnum) {
            case ERROR:
                assert error != null;
                return ResponseEntity.status(error.getStatus()).body(error);
            case SUCCESS:
                if (result != null) {
                    return ResponseEntity.ok(result);
                }
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}