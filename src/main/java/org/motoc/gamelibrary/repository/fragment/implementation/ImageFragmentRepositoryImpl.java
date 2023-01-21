package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Image;
import org.motoc.gamelibrary.domain.model.ImageBlob;
import org.motoc.gamelibrary.repository.fragment.ImageFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageFragmentRepositoryImpl implements ImageFragmentRepository {


    private final EntityManagerFactory emf;

    private static final Logger logger = LoggerFactory.getLogger(ImageFragmentRepositoryImpl.class);

    @Autowired
    public ImageFragmentRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Long persistImageAttachToGame(byte[] bytes, Long gameId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Game game = em.find(Game.class, gameId);

        Image i = new Image();
        i.setGame(game);
        return persistLob(bytes, em, i);
    }

    @Override
    public byte[] findBytes(Long imageId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        ImageBlob ib = em.find(ImageBlob.class, imageId);

        if (ib == null) {
            throw new NotFoundException("No image of id " + imageId + " found.");
        }

        byte[] bytes;

        bytes = ib.getContent();

        em.getTransaction().commit();
        em.close();
        return bytes;
    }

    @Override
    public Long persistImage(byte[] bytes) {
        EntityManager em = emf.createEntityManager();
        Image i = new Image();
        em.getTransaction().begin();
        em.persist(i);

        ImageBlob ib = new ImageBlob();
        ib.setImage(i);
        ib.setContent(bytes);
        em.persist(ib);
        logger.info("Image id :" + i.getId() + " and ImageBlob id :" + ib.getId());

        em.getTransaction().commit();
        em.close();

        return i.getId();
    }

    @Override
    public List<Long> persistAll(List<byte[]> bytesList) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Long> ids = new ArrayList<>();
        for (byte[] bytes : bytesList) {
            Image i = new Image();
            em.persist(i);

            ImageBlob ib = new ImageBlob();
            ib.setImage(i);
            ib.setContent(bytes);
            em.persist(ib);

            ids.add(i.getId());
        }

        em.getTransaction().commit();
        em.close();

        return ids;
    }

    public Long persistLob(byte[] bytes, EntityManager em, Image i) {
        em.persist(i);

        ImageBlob ib = new ImageBlob();
        ib.setImage(i);
        ib.setContent(bytes);
        em.persist(ib);

        em.getTransaction().commit();
        em.close();

        return i.getId();
    }
}
