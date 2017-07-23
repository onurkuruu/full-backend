package com.oonurkuru.backend.utils;

import com.oonurkuru.backend.exceptions.CustomException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Methodlar ile ilgili işlemleri gerçekleştirmek için tasarlanmıştır.
 */
public abstract class MethodUtils {

    /**
     * Verilen field ismine göre ilgili field'ın get metodunu bulur.
     *
     * @param field       bulunması istenen get metot
     * @param targetClass hangi sınıf içerisinde arama yapılacak
     * @return metot bulunursa geri göndürülür.
     */
    public static Method findGetMethod(String field, Class targetClass) {

        Method method = null;

        try {
            method = new PropertyDescriptor(field, targetClass).getReadMethod();
        } catch (IntrospectionException e) {
            throw new CustomException(300, "Reflection Error", "Get metotu bulunamadı. Field ismi: " + field);
        }

        return method;
    }

    /**
     * Verilen field ismine göre ilgili field'ın set metodunu bulur.
     *
     * @param field       bulunması istenen set metodu
     * @param targetClass hangi sınıf içerisinde arama yapılacak
     * @return metot bulunursa geri göndürülür.
     */
    public static Method findSetMethod(String field, Class targetClass) {

        Method method = null;

        try {
            method = new PropertyDescriptor(field, targetClass).getWriteMethod();
        } catch (IntrospectionException e) {
            throw new CustomException(300, "Reflection Error", "Set metotu bulunamadı. Field ismi: " + field);
        }

        return method;
    }


    /**
     * verilen field'ın get metotu bulunur ve çalıştırılır. Üretilen değer geri döndürülür.
     *
     * @param field  çalıştırılacak get metodu için ilişkili alan ismi
     * @param object verilen nesne üzerinden fonksiyon çalıştırılır.
     * @return değer geri döndürülür.
     */
    public static Object getValue(String field, Object object) throws CustomException {

        Object value;
        Method method = null;
        try {
            method = findGetMethod(field, object.getClass());
            value = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CustomException(301, "Reflection Error", "Get metotu çalıştırılamadı. Metot ismi: " + method.getName());
        }

        return value;
    }

    /**
     * verilen field'ın set metotu bulunur ve verilen parametre kullanılarak çalıştırılır.
     *
     * @param field  çalıştırılması istenen ilgili set metodu ile ilişlini alan adı.
     * @param value  verilen parametre kullanılarak fonksiyon çalıştırılır.
     * @param object verilen nesne üzerinden fonksiyon çalıştırılır.
     * @param <T>
     * @throws CustomException
     */
    public static <T> void setValue(String field, Object value, T object) throws CustomException {

        Method method = null;
        try {
            method = findSetMethod(field, object.getClass());
            method.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CustomException(301, "Reflection Error", "Set metotu çalıştırılamadı. Metot ismi: " + method.getName());
        }

    }
}
