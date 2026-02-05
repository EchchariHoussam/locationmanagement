package com.carmarketpro.common.api;

public class ErrorDetail {

    private String code;
    private String field;
    private String message;

    public ErrorDetail() {
    }

    public ErrorDetail(String code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ErrorDetailBuilder builder() {
        return new ErrorDetailBuilder();
    }

    public static final class ErrorDetailBuilder {
        private String code;
        private String field;
        private String message;

        private ErrorDetailBuilder() {
        }

        public ErrorDetailBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorDetailBuilder field(String field) {
            this.field = field;
            return this;
        }

        public ErrorDetailBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorDetail build() {
            return new ErrorDetail(code, field, message);
        }
    }
}
