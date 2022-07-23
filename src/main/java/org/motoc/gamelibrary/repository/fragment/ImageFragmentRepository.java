package org.motoc.gamelibrary.repository.fragment;

import java.io.InputStream;

/**
 * It's the image custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ImageFragmentRepository {

    Long persistLob(byte[] image, Long gameId);

    InputStream findBlob(Long imageId);
}
