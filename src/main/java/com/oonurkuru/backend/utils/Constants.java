package com.oonurkuru.backend.utils;

import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Onur Kuru on 23.7.2017.
 */
public class Constants {

    public static final String WRAPPER_PACKAGE_PREFIX = "java.lang";
    public static final String COLLECTION_PACKAGE_PREFIX = "java.util";
    public static final String CRITERIA_PAGE = "page";
    public static final String CRITERIA_LIMIT = "limit";
    public static final String CRITERIA_IGNORE_CASE = "ignorecase";
    public static final String CRITERIA_SORT_BY = "sortby";
    public static final String CRITERIA_SORT_DIRECTION = "direction";
    public static final String CRITERIA_DESC_SORT = "desc";
    public static final String CRITERIA_ASC_SORT = "asc";

    public static final Integer CRITERIA_PAGE_DEFAULT_VALUE = 0;
    public static final Integer CRITERIA_PAGE_MIN_VALUE = 0;
    public static final Integer CRITERIA_LIMIT_DEFAULT_VALUE = 10;
    public static final Integer CRITERIA_LIMIT_MIN_VALUE = 1;
    public static final Integer CRITERIA_LIMIT_MAX_VALUE = 100;
    public static final boolean CRITERIA_IGNORE_CASE_DEFAULT_VALUE = true;
    public static final String CRITERIA_SORT_BY_DEFAULT_VALUE = "id";
    public static final Sort.Direction CRITERIA_SORT_DIRECTION_DEFAULT_VALUE = Sort.Direction.ASC;

    public static final Set<String> criteriaList;

    static {
        criteriaList = new HashSet<>();
        criteriaList.add(CRITERIA_PAGE);
        criteriaList.add(CRITERIA_LIMIT);
        criteriaList.add(CRITERIA_IGNORE_CASE);
        criteriaList.add(CRITERIA_SORT_BY);
        criteriaList.add(CRITERIA_SORT_DIRECTION);
    }
}
