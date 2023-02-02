package org.motoc.gamelibrary.technical;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains a method to load resources as bytes.
 */
public class ResourceLoader {

    /**
     * This method loads a resource from the given location and returns it as a byte array.
     *
     * @param location The location of the resource.
     * @return The resource as a byte array.
     * @throws IOException if the resource cannot be loaded.
     */
    public static byte[] getBytesFromResource(String location) throws IOException {
        try (InputStream is = ResourceLoader.class.getResourceAsStream(location)) {
            assert is != null;
            return is.readAllBytes();
        } catch (NullPointerException npe) {
            return null;
        }
    }
}
