package org.motoc.gamelibrary.repository.fragment.implementation;

import org.hibernate.engine.jdbc.BlobProxy;
import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.domain.model.Image;
import org.motoc.gamelibrary.domain.model.ImageBlob;
import org.motoc.gamelibrary.repository.fragment.ImageFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.InputStream;
import java.sql.SQLException;

@Repository
public class ImageFragmentRepositoryImpl implements ImageFragmentRepository {

    private final EntityManagerFactory emf;

    @Autowired
    public ImageFragmentRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Long persistLob(byte[] bytes, Long gameId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Game game = em.find(Game.class, gameId);

        Image i = new Image();
        i.setGame(game);
        em.persist(i);

        ImageBlob ib = new ImageBlob();
        ib.setImage(i);
        ib.setContent(BlobProxy.generateProxy(bytes));
        em.persist(ib);

        em.getTransaction().commit();
        em.close();

        return i.getId();
    }

    @Override
    public InputStream findBlob(Long imageId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        ImageBlob ib = em.find(ImageBlob.class, imageId);

        if (ib == null || ib.getContent() == null) {
            throw new NotFoundException("No image of id " + imageId + " found.");
        }
        try {
            return ib.getContent().getBinaryStream();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
