package com.curtisnewbie.impl;

import com.curtisnewbie.api.UrlUtil;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that provides util methods for URL string
 *
 * @author zhuangyongj
 */
@Component
public class UrlUtilImpl implements UrlUtil {

    @Override
    public String parseBaseUrl(String url) {
        final String EMPTY = "";
        if (url == null || url.isBlank()) {
            return EMPTY;
        }
        Pattern pat = Pattern.compile("^[Hh][Tt][Tt][Pp][Ss]?://([^/]*).*$");
        Matcher mat = pat.matcher(url);
        if (mat.find()) {
            return mat.group(1);
        }
        return EMPTY;
    }
}
