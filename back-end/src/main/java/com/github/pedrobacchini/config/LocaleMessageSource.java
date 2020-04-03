package com.github.pedrobacchini.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Component
public class LocaleMessageSource {

    //    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private final MessageSource messageSource;

    public LocaleMessageSource(MessageSource messageSource) { this.messageSource = messageSource; }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(DEFAULT_LOCALE);
        return slr;
    }

    public String getMessage(String code) { return messageSource.getMessage(code, null, DEFAULT_LOCALE); }

    public String getMessage(String code, Object... args) { return messageSource.getMessage(code, args, DEFAULT_LOCALE); }
}
