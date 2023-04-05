package org.motoc.gamelibrary.technical.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("files")
@Getter
@Setter
public class PathProperties {

    /**
     * <p>The root file path that lead to the image directory. The location will then append the filename to define
     * * the full path of each image.</p>
     */
    private String pathRoot = "/opt/game-library/image/";
}
