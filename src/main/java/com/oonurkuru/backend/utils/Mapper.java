package com.oonurkuru.backend.utils;

import com.oonurkuru.backend.exceptions.CustomException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Onur Kuru on 22.7.2017.
 */

/**
 * Domain - DTO , DTO - Domain işlemlerinin gerçekleştirilmesi için kullanılan fonksiyonları içerir.
 * Wrapper ve primitive, List için test edilmiştir.
 */
public abstract class Mapper {

    /**
     * Domain - DTO, DTO - Domain dönüşümleri için hazırlanmıştır.
     * Wrapper, primitive, Collection, kullanıcı tanımlı tipler için dönüşüm yapabilir.
     * Domain ve DTO içerisinde ki alan isimleri aynı olmalıdır.
     * Dönüşüm yapılacak sınıf @Mapper notasyonu ile belirtilemelidir. Örn Employee sınıfından EmployeeDTO sınıfına dönüşüm için
     * Employee sınıfı @Mapper(targetClass="EmployeeDTO.class") olarak belirtilmelidir.
     *
     * @param sourceObject değerlerin alınacağı sınıfı belirtir.
     * @param deeply       domain-dto dönüşümlerinde lazy-initialization hatasını önlemek için kullanılmıştır. Eğer parametre
     *                     true olarak verilirse kullanıcı tanımlı sınıfları ve Collection sınıflarını dönüştürmeye zorlar.
     *                     <p>
     *                     eğer false olarak verilirse kullanıcı tanımlı sınıfları ve Collection sınıflarını es geçerek dönüştürme
     *                     işlemini tamamlar.
     * @param <T>          Değerlerin alınacağı sınıf tipinin bağımsız olması sağlanmıştır.
     * @return Dönüştürülen nesne geri döndürülür.
     * @throws CustomException Dönüşüm işlemlerisi sırasında hata oluşması durumunda üst katmanlara bildirilir.
     */
    public static <T> Object objectMapper(T sourceObject, boolean deeply) throws CustomException {

        Field[] fields = sourceObject.getClass().getDeclaredFields();//Dönüştürme yapılacak alan isimleri alınıyor
        Object newInstance = ClassUtils.getTargetClassNewInstanceByAnnotation(sourceObject.getClass()); // hedef sınıf nesnesi oluşturuluyor.

        Arrays.stream(fields)
                .forEach((Field field) -> {
                    Object sourceValue = MethodUtils.getValue(field.getName(), sourceObject);//kaynak nesnede ki ilgili alanın değeri alınıyor.

                    if (FieldUtils.isWrapperOrPrimitive(field)) {//alan tipinin primitive ya da wrapper olması durumunda direkt hedef nesneye ilgili değer atanır.
                        MethodUtils.setValue(field.getName(), sourceValue, newInstance);
                    } else if (FieldUtils.isCollection(field) && deeply) { //alan tipinin Collection olması durumunda tüm alanlar forEach ile dönülerek tek tek atama yapılır.

                        List<T> sourceList = new ArrayList<>((Collection<? extends T>) sourceValue);
                        List targetListInstance = (List) objectMapper(sourceList, false);
                        MethodUtils.setValue(field.getName(), targetListInstance, newInstance);

                    } else if (deeply) { //alan isminin kullanıcı tanımlı olması durumunda alt alanları atanması için bu fonksiyon kendini çağırıyor.
                        MethodUtils.setValue(field.getName(), objectMapper(sourceValue, false), newInstance);
                    }
                });

        return newInstance;
    }


    /**
     * Object Mapper fonksiyona Collection sınıflarına destek vermesi için oluşturulmuştur.
     *
     * @param sourceObject
     * @param deeply
     * @param <T>
     * @return
     * @throws CustomException
     */
    public static <T> Object objectMapper(Collection<T> sourceObject, boolean deeply) throws CustomException {

        List targetListInstance = new ArrayList();
        sourceObject.forEach(object -> targetListInstance.add(objectMapper(object, deeply)));

        return targetListInstance;
    }

}
