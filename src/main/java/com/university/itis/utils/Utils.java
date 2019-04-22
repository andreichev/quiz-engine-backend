package com.university.itis.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 09.02.2018.
 */
public class Utils {
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
