package org.motoc.gamelibrary.configuration;

import org.motoc.gamelibrary.technical.properties.PathProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PathProperties.class)
public class AppConfiguration {
}
