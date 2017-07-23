package com.oonurkuru.backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Domain - DTO , DTO - Domain dönüşümlerinde dönüştürülecek hedef sınıfı belirler.
 * Örneğin Employee sınıfı için dönüştürülmesi istenen sınıf EmployeeDTO sınıfıdır.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapper {
    Class targetClass();
}
