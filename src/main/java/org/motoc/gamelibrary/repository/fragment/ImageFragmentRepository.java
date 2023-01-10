package org.motoc.gamelibrary.repository.fragment;

import java.io.InputStream;
import java.util.List;

/**
 * It's the image custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ImageFragmentRepository {

    Long persistImageAttachToGame(byte[] bytes, Long gameId);

    InputStream findLob(Long imageId);

    Long persistImage(byte[] bytes);


    List<Long> persistAll(List<byte[]> bytesList);
}
