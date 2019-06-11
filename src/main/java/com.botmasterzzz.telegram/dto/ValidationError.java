package com.botmasterzzz.telegram.dto;

public class ValidationError {

    private boolean hasError;
    private String errorReason;

    public boolean hasError() {
        return null != errorReason;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
