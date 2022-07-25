package org.motoc.gamelibrary.technical.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "files")
public class PathProperties {

    /**
     * <p>The root file path that lead to the image directory. The location will then append the filename to define
     * * the full path of each image.</p>
     */
    private String pathRoot = "/opt/game-library/image/";

    public String getPathRoot() {
        return pathRoot;
    }

    public void setPathRoot(String pathRoot) {
        this.pathRoot = pathRoot;
    }
}
