package com.procurement.point.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ocds")
public class OCDSProperties {

    private String[] extensions;

    private String path;

    private String version;

    private String license;

    private String publicationPolicy;

    private String publisherName;

    private String publisherScheme;

    private String publisherUid;

    private String publisherUri;

}
