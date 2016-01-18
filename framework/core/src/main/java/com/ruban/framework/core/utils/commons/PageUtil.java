package com.ruban.framework.core.utils.commons;

public class PageUtil {

    public static int getTotalPages(final int totalRecords, final int recordsPerPage) {
        return (totalRecords % recordsPerPage == 0) ? totalRecords / recordsPerPage
                : (totalRecords / recordsPerPage) + 1;
    }
}
