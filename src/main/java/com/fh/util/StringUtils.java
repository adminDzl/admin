package com.fh.util;

import com.google.common.base.Strings;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return Strings.isNullOrEmpty(str) || "null".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
