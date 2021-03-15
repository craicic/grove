package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Seller;
import org.motoc.gamelibrary.repository.criteria.GameCopyRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.GameCopyRepository;
import org.motoc.gamelibrary.repository.jpa.SellerRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GameCopyService extends SimpleCrudMethodsImpl<GameCopy, JpaRepository<GameCopy, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyService.class);

    private final GameCopyRepository copyRepository;

    private final GameCopyRepositoryCustom copyRepositoryCustom;

    private final SellerRepository sellerRepository;

    public GameCopyService(JpaRepository<GameCopy, Long> genericRepository,
                           GameCopyRepository copyRepository,
                           GameCopyRepositoryCustom copyRepositoryCustom,
                           SellerRepository sellerRepository) {
        super(genericRepository, GameCopy.class);
        this.copyRepository = copyRepository;
        this.copyRepositoryCustom = copyRepositoryCustom;
        this.sellerRepository = sellerRepository;
    }

    public GameCopy findByObjectCode(String objectCode) {
        return null;
    }

    public GameCopy edit(GameCopy newCopy, Long id) {
        return copyRepository.findById(id)
                .map(copy -> {
                    copy.setObjectCode(newCopy.getObjectCode());
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
                    return copyRepositoryCustom.addSeller(copy, seller);
                })
                .orElseThrow(() -> {
                            throw new NotFoundException("No copy of id=" + copyId + " found.");
                        }
                );
    }

    public void removeSeller(Long copyId, Long sellerId) {
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

                    copyRepositoryCustom.removeSeller(copy, seller);
                }, () -> {
                    throw new NotFoundException("No copy of id=" + copyId + " found.");
                });

    }
}
