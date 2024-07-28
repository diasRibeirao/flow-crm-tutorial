package com.example.application;

import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.Cookie;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nProvider {
    private static final String BUNDLE_NAME = "i18n.messages";

    private static final String LOCALE_COOKIE_NAME = "user-locale";

    public static String getTranslation(String key) {
        return getTranslation(key, getCurrentLocale());
    }

    public static String getTranslation(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        return bundle.getString(key);
    }

    public static Locale getCurrentLocale() {
        Locale locale = getLocaleFromCookie();
        if (locale == null) {
            if (VaadinSession.getCurrent() != null) {
                locale = VaadinSession.getCurrent().getLocale();
            }
        }

        if (locale == null) {
            locale = Locale.ENGLISH;
            if (VaadinSession.getCurrent() != null) {
                VaadinSession.getCurrent().setLocale(locale);
            }
        }

        return locale;
    }

    public static void saveLocaleToCookie(Locale locale) {
        String localeString = locale.toLanguageTag();
        Cookie localeCookie = new Cookie(LOCALE_COOKIE_NAME, localeString);
        localeCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        localeCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        VaadinService.getCurrentResponse().addCookie(localeCookie);
    }

    public static Locale getLocaleFromCookie() {
        VaadinServletRequest request = (VaadinServletRequest) VaadinService.getCurrentRequest();

        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (LOCALE_COOKIE_NAME.equals(cookie.getName())) {
                        return Locale.forLanguageTag(cookie.getValue());
                    }
                }
            }
        }
        return null;
    }

}
