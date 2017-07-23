package com.oonurkuru.backend.utils;

import com.oonurkuru.backend.exceptions.CustomException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Onur Kuru on 23.7.2017.
 */

/**
 * Spring data sınıfları kullanılarak sorgular oluşturmak için geliştirilen fonksiyonları içerir.
 * Sorgular and ile ile bağlanır or desteği yoktur.
 * Kullanıcı tanımlı alt alanlar için sorgulama desteği yoktur.
 * Kullanıcıdan alınan değerlerın case sensitive değeri ve başta sonda ya da ortada arama seçeneği yoktur. varsılan olarak
 * case ihmal edilir ve like %değer% şeklinde arma yapılır.
 */
public abstract class QueryUtils {

    /**
     * Spring data PageRequest sınıfı kullanılarak sayfalama işlemleri gerçekleştiriliyor. Kullanıcıdan alınan sayfalama
     * kriterleri ve default değerler kullanılarak PageRequest sınıfı oluşturulur.
     *
     * @param queryMap kullanıcıdan alınan kriterler
     * @return
     */
    public static PageRequest createPageRequest(MultivaluedMap<String, String> queryMap) {

        //default değerler atanıyor.
        Integer page = Constants.CRITERIA_PAGE_DEFAULT_VALUE;
        Integer size = Constants.CRITERIA_LIMIT_DEFAULT_VALUE;
        String[] sortBy = new String[]{Constants.CRITERIA_SORT_BY_DEFAULT_VALUE};
        Sort.Direction direction = Constants.CRITERIA_SORT_DIRECTION_DEFAULT_VALUE;

        for (Map.Entry<String, List<String>> entrySet : queryMap.entrySet()) {
            if (FieldUtils.isCriteriaField(entrySet.getKey().toLowerCase())) {//kullanıcıdan alınan parametreler sayfalama kriteri ile eşleşiyorsa

                switch (entrySet.getKey().toLowerCase()) {
                    case Constants.CRITERIA_PAGE://eğer belirtilen kriter sayfa numarası ve geçerli bir değerse ata
                        page = getValueFromStringList(entrySet.getValue(), page);
                        page = page < Constants.CRITERIA_PAGE_MIN_VALUE ? Constants.CRITERIA_PAGE_DEFAULT_VALUE : page;
                        break;
                    case Constants.CRITERIA_LIMIT://eğer belirtilen kriter limit sayısı ve geçerli bir değerse ata
                        size = getValueFromStringList(entrySet.getValue(), size);
                        size = size < Constants.CRITERIA_LIMIT_MIN_VALUE || size > Constants.CRITERIA_LIMIT_MAX_VALUE ? Constants.CRITERIA_LIMIT_DEFAULT_VALUE : size;
                        break;
                    case Constants.CRITERIA_SORT_DIRECTION://sıralama kriterini belirtir.
                        direction = getValueFromStringList(entrySet.getValue(), direction);
                        break;
                }
            }
        }

        PageRequest pageRequest;

        try {
            pageRequest = new PageRequest(page, size, direction, sortBy);
        } catch (Exception e) {
            throw new CustomException(200, "Query Error", "Page Request Oluşturulamadı");
        }

        return pageRequest;
    }


    /**
     * Spring data Example sınıfı kullanılarak sorgu oluşturulması sağlanır.
     *
     * @param queryMap    kullanıcıdan alınan kriterler ilgili sınıfın bir alanını işaret ediyorsa kriter olarak eklenir.
     * @param targetClass sorgulamada kullanılacak sınıf örneği oluşturmak için kullanılır.
     * @param <T>
     * @return oluşturulan Example sınıfı geri döndürülür.
     * @throws CustomException
     */
    public static <T> Example<T> createExample(MultivaluedMap<String, String> queryMap, Class targetClass) throws CustomException {

        Object targetObject = ClassUtils.getNewInstance(targetClass);//ilgili sınıfın bir örneği oluşturuluyor.

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase()//değerlerin case duyarlılığı olmadan ve kelimenin içinde aranağı belirtiliyor like %değer% gibi
                .withIncludeNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<String> namesOfFields = Arrays.stream(targetClass.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());

        queryMap.forEach((key, value) -> {
            if (namesOfFields.contains(key)) {//eğer kullanıdan alınan kriter bu sınıfın bir alan ismine denk geliyorsa
                namesOfFields.remove(key);    //sınıfın tüm field isimlerini içeren sınıfdan ilgili değer silinir.
                MethodUtils.setValue(key, value.get(0), targetObject); //oluşturulan sınıf örneğine kullanıcıdan alınan değer set edilir.
            }
        });

        //geri kalan alan isimlerinin sorgulama sırasında ihmal edilmesi için withIgnorePaths kullanılır.
        exampleMatcher = exampleMatcher.withIgnorePaths(namesOfFields.toArray(new String[namesOfFields.size()]));

        Example<T> exampleClass;

        try {
            exampleClass = Example.of((T) targetObject, exampleMatcher);
        } catch (Exception e) {
            throw new CustomException(200, "Query Error", "Example Sınıfı Oluşturulamadı");
        }

        return exampleClass;
    }


    /**
     * Kullanıcıdan alınan kriterlerin geçerliliğini kontrol edip uygun bulunması durumunda yeni değerlerini döndüren fonksiyon.
     */
    public static <T> T getValueFromStringList(List<String> valueList, T defaultValue) {

        if (valueList == null) {
            return defaultValue;
        }

        if (defaultValue instanceof Integer) {

            String value = valueList.get(0);
            return StringUtils.isEmpty(value) ? defaultValue : (T) Integer.valueOf(value);

        } else if (defaultValue instanceof Sort.Direction) {

            String value = valueList.get(0);

            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }

            return (T) (Constants.CRITERIA_DESC_SORT.equalsIgnoreCase(value) ? Sort.Direction.DESC : Sort.Direction.ASC);
        }
        return defaultValue;
    }

}
