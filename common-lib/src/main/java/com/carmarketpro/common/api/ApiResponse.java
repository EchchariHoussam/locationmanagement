package com.carmarketpro.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Format de réponse standard pour toutes les API CarMarket Pro.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private List<ErrorDetail> errors = new ArrayList<>();
    private Meta meta;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, List<ErrorDetail> errors, Meta meta) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors != null ? errors : new ArrayList<>();
        this.meta = meta;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors != null ? errors : new ArrayList<>();
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(true);
        r.setData(data);
        return r;
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(true);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static <T> ApiResponse<T> ok(T data, Meta meta) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(true);
        r.setData(data);
        r.setMeta(meta);
        return r;
    }

    public static <T> ApiResponse<T> fail(String message, List<ErrorDetail> errors) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(false);
        r.setMessage(message);
        r.setErrors(errors != null ? errors : new ArrayList<>());
        return r;
    }

    public static <T> ApiResponse<T> fail(String message) {
        return fail(message, new ArrayList<>());
    }
}
