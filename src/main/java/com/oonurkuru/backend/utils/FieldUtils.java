package com.oonurkuru.backend.utils;

import java.lang.reflect.Field;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Field'lar ile ilgili işlemler yapıldığı fonksiyonları içerir.
 */
public abstract class FieldUtils {


    /**
     * Verilen field'ın tipinin wrapper sınıf ya da primitive tip olup olmadığına bakar.
     * Wrapper sınıf olma şartı için: sınıfın bulunduğu paket isminin "java.lang" ile başlamasına
     * Primitive tip olma şartı için ise Class.isPrimitive() fonksiyonu kullanılmıştır.
     *
     * @param field kontrol edilecek alan
     * @return şartlardan birini sağlaması durumunda true diğer durumlarda false döndürür.
     */
    public static boolean isWrapperOrPrimitive(Field field) {
        return field.getType().getName().startsWith(Constants.WRAPPER_PACKAGE_PREFIX) || field.getType().isPrimitive();
    }


    /**
     * Verilen field'ın tipinin collection sınıf üyesi olup olmadığına bakar
     * Kontrol edilen şart: ilgili sınıfın "java.util" paketi altında bulunması
     *
     * @param field kontrol edilecek alan
     * @return şartı sağlaması durumunda true, sağlamazsa false döndürür.
     */
    public static boolean isCollection(Field field) {
        return field.getType().getName().startsWith(Constants.COLLECTION_PACKAGE_PREFIX);
    }

    /**
     * Field isminin @GET metotlarında arama için kullanılan alan isimlerinden biri olup olmadığını kontrol eder.
     * Arama için kullanılan kelimeler Constants.CRITERIA_xxx' dir.
     *
     * @param fieldName aranacak alan ismi
     * @return şartı sağlaması durumunda true, sağlamazsa false
     */
    public static boolean isCriteriaField(String fieldName) {
        return Constants.criteriaList.contains(fieldName);
    }

}
