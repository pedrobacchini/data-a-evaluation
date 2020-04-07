package com.github.pedrobacchini.config;

import com.github.pedrobacchini.service.impl.FileArchiveServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("backend")
public class BackEndProperty {

    @Getter
    private final Image image = new Image();

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Image {

        @Getter
        private final Candidate candidate = new Candidate();

        @Getter
        @Setter
        public static class Candidate {
            private String prefix;
            private int size;

            public String getBaseUrl() { return FileArchiveServiceImpl.getBaseUrl() + prefix; }
        }
    }
}
