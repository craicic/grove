package org.motoc.gamelibrary.technical.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("openapi")
public record OpenApiProperties(String projectTitle, String projectDescription, String projectVersion) {
}