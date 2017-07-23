package com.oonurkuru.backend.utils;

import com.oonurkuru.backend.annotations.Mapper;
import com.oonurkuru.backend.exceptions.CustomException;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Classlar ile ilgili işlemleri içerir
 */
public abstract class ClassUtils {

    /**
     * Mapper notasyonu ile işaretlenmiş sınıf okunarak oluşturulacak target sınıf nesnesi belirtilir.
     *
     * @param clazz Mapper notasyonu ile işaretlenmiş sınıf gereklidir. Bu sınıftan okunan targetClass özelliği okunarak
     *              oluşturulacak nesnenin tipine karar verilir.
     * @param <T>   Yeni oluşturulan nesnenin tipini belirler.
     * @return Oluşturulan nesne geri döndürülür.
     */
    public static <T> T getTargetClassNewInstanceByAnnotation(Class clazz) {
        Mapper annotationValue = (Mapper) clazz.getAnnotation(Mapper.class);

        T newInstance;
        try {
            newInstance = (T) annotationValue.targetClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CustomException(302, "Reflection Error", "Target Sınıf örneği oluşturulamadı");
        }
        return newInstance;
    }

    /**
     * Yeni bir nesne oluşturmak için kullanılır. Verilen parametreye tipinde bir nesne oluştururak geri döndürür.
     *
     * @param clazz oluşturulacak nesnenin tipini belirler.
     * @param <T>   üretilecek nesnenin tipini dinamik olmasını sağlar
     * @return oluşturulan nesne geri döndürülür.
     */
    public static <T> T getNewInstance(Class clazz) {

        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CustomException(302, "Reflection Error", "Sınıf örneği oluşturulamadı");
        }

    }

}
