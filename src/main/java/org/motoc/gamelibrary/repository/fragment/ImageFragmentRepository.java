package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.technical.exception.NotFoundException;

import java.util.List;

/**
 * It's the image custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface ImageFragmentRepository {

    /**
     * This method persists an image to the database and attaches it to a Game.
     * It creates a new Image entity, sets the image's game to the game with the given ID, and persists the image to the database.
     *
     * @param bytes  The byte array representing the image content.
     * @param gameId The ID of the game to which the image will be attached.
     * @return The ID of the persisted Image.
     */
    Long persistImageAndAttachToGame(byte[] bytes, Long gameId);


    /**
     * This method finds the bytes of an image by its ID.
     *
     * @param imageId The ID of the image to find.
     * @return The byte array representing the image content.
     * @throws NotFoundException if an image of the given ID is not found.
     */
    byte[] findBytes(Long imageId);

    /**
     * This method persists an image to the database.
     * It creates a new Image and ImageBlob entities, sets the ImageBlob's content to the given byte array, and persists both entities to the database.
     *
     * @param bytes The byte array representing the image content.
     * @return The ID of the persisted Image.
     */
    Long persistImage(byte[] bytes);

    /**
     * This method persists a List of images to the database.
     * It creates new Image and ImageBlob entities for each image in the list, sets the ImageBlob's content to the corresponding byte array, and persists both entities to the database.
     *
     * @param bytesList A list of byte arrays representing the images content.
     * @return A list of IDs of the persisted Images.
     */
    List<Long> persistAll(List<byte[]> bytesList);
}
