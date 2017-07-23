package com.oonurkuru.backend.exceptions.models;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Kullanıcı bilgilendirmek için kullanılacak model.
 * ErrorCode alanı ile hatanın kod numarası: 1xx hata kodları ile CRUD işlemleri hataları, 2xx ile sorgu hataları, 3xx
 * ile reflection hataları 4xx ile handle edilmemiş hatalar kodlanır
 * ErrorKey alanı ile hatanın kısa kodu
 * Message alanı ile geliştirici mesajı kullanıcıya iletilir.
 */
public class ExceptionModel {

    private Integer errorCode;
    private String errorKey;
    private String message;

    public ExceptionModel(Integer errorCode, String errorKey, String message) {
        this.errorCode = errorCode;
        this.errorKey = errorKey;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
