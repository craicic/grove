package org.motoc.gamelibrary.repository.fragment.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.motoc.gamelibrary.domain.dto.ImageDto;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Image;
import org.motoc.gamelibrary.domain.model.ImageBlob;
import org.motoc.gamelibrary.repository.fragment.ImageFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Long persistImageAndAttachToGame(byte[] bytes, Long gameId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Game game = em.find(Game.class, gameId);

        if (game == null) {
            em.getTransaction().rollback();
            em.close();
            throw new NotFoundException("No game of id=" + gameId + " found.");
        }

        Image i = new Image();
        i.setGame(game);
        return persistLob(bytes, em, i);
    }

    @Override
    public ImageDto persistByteToImage(byte[] bytes, Long gameId) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Game game = new Game();
        game.setId(gameId);

        Image i = new Image();

        i.setGame(game);
        em.persist(i);

        ImageBlob ib = new ImageBlob();

        ib.setImage(i);
        ib.setContent(bytes);
        em.persist(ib);

        ImageDto dto = new ImageDto(i.getId(), ib.getContent());

        em.getTransaction().commit();
        em.close();

        return dto;
    }

    @Override
    public byte[] findBytes(Long imageId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        ImageBlob ib = em.find(ImageBlob.class, imageId);

        if (ib == null) {
            em.getTransaction().rollback();
            em.close();
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

    @Override
    public void deleteLob(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Image i = em.find(Image.class, id);
        ImageBlob ib = em.find(ImageBlob.class, id);

        em.remove(ib);
        em.remove(i);

        em.getTransaction().commit();
        em.close();
    }

    private Long persistLob(byte[] bytes, EntityManager em, Image i) {
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
