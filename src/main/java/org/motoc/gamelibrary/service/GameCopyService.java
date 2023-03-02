package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.domain.model.Seller;
import org.motoc.gamelibrary.repository.jpa.GameCopyRepository;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.motoc.gamelibrary.repository.jpa.SellerRepository;
import org.motoc.gamelibrary.service.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameCopyService extends SimpleCrudMethodsImpl<GameCopy, JpaRepository<GameCopy, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyService.class);

    private final GameCopyRepository copyRepository;


    private final SellerRepository sellerRepository;

    private final PublisherRepository publisherRepository;

    private GameCopy gameCopyToReturn;

    public GameCopyService(JpaRepository<GameCopy, Long> genericRepository,
                           GameCopyRepository copyRepository,
                           SellerRepository sellerRepository,
                           PublisherRepository publisherRepository) {
        super(genericRepository, GameCopy.class);
        this.gameCopyToReturn = null;
        this.copyRepository = copyRepository;
        this.sellerRepository = sellerRepository;
        this.publisherRepository = publisherRepository;
    }

    public GameCopy findByObjectCode(String code) {

        GameCopy copy = copyRepository.findByObjectCode(code);

        if (copy == null) {
            throw new NotFoundException("No existing game has the following code :" + code);
        }
        return copy;
    }

    public GameCopy edit(GameCopy newCopy, Long id) {
        return copyRepository.findById(id)
                .map(copy -> {
                    copy.setObjectCode(newCopy.getObjectCode());
                    copy.setEditionNumber(newCopy.getEditionNumber());
                    copy.setLocation(newCopy.getLocation());
                    copy.setPrice(newCopy.getPrice());
                    copy.setDateOfPurchase(newCopy.getDateOfPurchase());
                    copy.setWearCondition(newCopy.getWearCondition());
                    copy.setGeneralState(newCopy.getGeneralState());
                    copy.setLoanable(newCopy.isLoanable());
                    logger.debug("Found game copy of id={}, proceeding to its edit", id);
                    return copyRepository.save(copy);
                })
                .orElseGet(() -> {
                    newCopy.setId(id);
                    logger.debug("No game copy of id={} found. Set game copy : {}", id, newCopy);
                    return copyRepository.save(newCopy);
                });
    }

    public GameCopy addSeller(Long copyId, Long sellerId) {
        Seller seller = sellerRepository
                .findById(sellerId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Seller of id=" + sellerId + " found.");
                        }
                );
        return copyRepository
                .findById(copyId)
                .map(copy -> {
                    if (copy.getSeller() == seller)
                        logger.warn("Game copy of id=" + copy.getId() +
                                " is already linked to the given publisher of id=" + seller.getId());
                    if (copy.getSeller() != null)
                        throw new IllegalStateException("Game copy of id=" + copy.getId() +
                                " is already linked to another seller of id=" + copy.getSeller().getId());
                    return copyRepository.addSeller(copy, seller);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No copy of id=" + copyId + " found.");
                        }
                );
    }

    public GameCopy removeSeller(Long copyId, Long sellerId) {
        this.gameCopyToReturn = null;

        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> {
                    throw new NotFoundException("No seller of id={}" + sellerId + " found.");
                }
        );

        copyRepository
                .findById(copyId)
                .ifPresentOrElse(copy -> {
                    if (copy.getSeller() == null || copy.getSeller() != seller)
                        throw new IllegalStateException("Copy of id=" + copy.getId() +
                                " is not linked to seller of id=" + seller.getId());

                    this.gameCopyToReturn = copyRepository.removeSeller(copy, seller);
                }, () -> {
                    throw new NotFoundException("No copy of id=" + copyId + " found.");
                });
        return this.gameCopyToReturn;
    }

    public GameCopy addPublisher(Long copyId, Long publisherId) {
        Publisher publisher = publisherRepository
                .findById(publisherId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Publisher of id=" + publisherId + " found.");
                        }
                );
        return copyRepository
                .findById(copyId)
                .map(gameCopy -> {
                    if (gameCopy.getPublisher() == publisher)
                        logger.warn("Game copy of id=" + gameCopy.getId() +
                                " is already linked to the given publisher of id=" + publisher.getId());
                    if (gameCopy.getPublisher() != null)
                        throw new IllegalStateException("Game copy of id=" + gameCopy.getId() +
                                " is already linked to another publisher of id=" + gameCopy.getPublisher().getId());
                    return copyRepository.addPublisher(gameCopy, publisher);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No game copy of id=" + copyId + " found.");
                        }
                );
    }

    public GameCopy removePublisher(Long copyId, Long publisherId) {
        this.gameCopyToReturn = null;
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> {
                    throw new NotFoundException("No publisher of id={}" + publisherId + " found.");
                }
        );

        copyRepository
                .findById(copyId)
                .ifPresentOrElse(gameCopy -> {
                    if (gameCopy.getPublisher() == null || gameCopy.getPublisher() != publisher)
                        throw new IllegalStateException("Game copy of id=" + gameCopy.getId() +
                                " is not linked to publisher of id=" + publisher.getId());

                    this.gameCopyToReturn = copyRepository.removePublisher(gameCopy, publisher);
                }, () -> {
                    throw new NotFoundException("No game copy of id=" + copyId + " found.");
                });
        return this.gameCopyToReturn;
    }

    public void checkLoanability(Long gameCopyId) {
        String errorMessage = "";
        // find game copy by id
        // if no result throw exception
        GameCopy gc = this.findById(gameCopyId);
        if (gc == null) {
            // todo useless??
            errorMessage = "No game copy of id=" + gameCopyId + " found in database.";
            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }

        // check if game has marker 'loanable'
        // if not throw exception
        if (!gc.isLoanable()) {
            errorMessage = "Game copy of id=" + gameCopyId + " is set to Not Loanable";
            logger.warn(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    public Page<GameCopy> findLoanReadyPage(Pageable pageable) {
        return copyRepository.findPageByLoanability(pageable);
    }

    public List<GameCopy> findLoanReady() {
        return copyRepository.findByLoanability();
    }
}