package com.huang.springbootepidemic.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocalResolver implements LocaleResolver {
    /**
     * Resolve the current locale via the given request.
     * Can return a default locale as fallback in any case.
     *
     * @param request the request to resolve the locale for
     * @return the current locale (never {@code null})
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lan = request.getParameter("lan");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(lan)){
            String[] subStr=lan.split("_");
            locale = new Locale(subStr[0],subStr[1]);
        }
        return locale;
    }

    /**
     * Set the current locale to the given one.
     *
     * @param request  the request to be used for locale modification
     * @param response the response to be used for locale modification
     * @param locale   the new locale, or {@code null} to clear the locale
     * @throws UnsupportedOperationException if the LocaleResolver
     *                                       implementation does not support dynamic changing of the locale
     */
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
