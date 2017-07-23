package com.oonurkuru.backend.exceptions;

import com.oonurkuru.backend.exceptions.models.ExceptionModel;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Son kullanıcıya gösterilecek hatayı rest servislere kadar taşır.
 * İçerisinde bulunan ExceptionModel alanları kullanılarak kullanıcı bilgilendirilir.
 */
public class CustomException extends RuntimeException {

    private ExceptionModel exceptionModel;

    public CustomException() {
        super();
    }

    public CustomException(Integer errorCode, String errorKey, String message) {
        super(message);
        this.exceptionModel = new ExceptionModel(errorCode, errorKey, message);
    }

    public ExceptionModel getExceptionModel() {
        return exceptionModel;
    }

    public void setExceptionModel(ExceptionModel exceptionModel) {
        this.exceptionModel = exceptionModel;
    }
}
