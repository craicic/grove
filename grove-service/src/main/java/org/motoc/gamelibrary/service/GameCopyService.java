package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.GameCopyDto;
import org.motoc.gamelibrary.domain.model.GameCopy;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.mapper.GameCopyMapper;
import org.motoc.gamelibrary.repository.jpa.GameCopyRepository;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@Service
@Transactional
public class GameCopyService {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyService.class);

    private final GameCopyRepository copyRepository;

    private final PublisherRepository publisherRepository;
    private final GameCopyMapper mapper;
    private GameCopy gameCopyToReturn;

    public GameCopyService(GameCopyRepository copyRepository,
                           PublisherRepository publisherRepository) {
        this.mapper = GameCopyMapper.INSTANCE;
        this.gameCopyToReturn = null;
        this.copyRepository = copyRepository;
        this.publisherRepository = publisherRepository;
    }

    public GameCopyDto save(@Valid GameCopyDto gc) {
        return mapper.copyToDto(copyRepository.save(mapper.dtoToCopy(gc)));
    }


    public long count() {
        return copyRepository.count();
    }


    public GameCopyDto findById(long id) {
        return mapper.copyToDto(copyRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No game copy of id=" + id + " found.");
                }));
    }


    public Page<GameCopyDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(copyRepository.findAll(pageable));
    }


    public GameCopyDto findByObjectCode(String code) {

        GameCopy copy = copyRepository.findByObjectCode(code);

        if (copy == null) {
            throw new NotFoundException("No existing game copy has the following code :" + code);
        }
        return mapper.copyToDto(copy);
    }

    public GameCopyDto edit(GameCopyDto copyDto, Long id) {
        GameCopy newCopy = mapper.dtoToCopy(copyDto);
        return mapper.copyToDto(copyRepository.findById(id)
                .map(copy -> {
                    copy.setObjectCode(newCopy.getObjectCode());
                    copy.setEditionNumber(newCopy.getEditionNumber());
                    copy.setLocation(newCopy.getLocation());
                    copy.setPrice(newCopy.getPrice());
                    copy.setDateOfPurchase(newCopy.getDateOfPurchase());
                    copy.setWearCondition(newCopy.getWearCondition());
                    copy.setGeneralState(newCopy.getGeneralState());
                    copy.setAvailableForLoan(newCopy.isAvailableForLoan());
                    logger.debug("Found game copy of id={}, proceeding to its edit", id);
                    return copyRepository.save(copy);
                })
                .orElseGet(() -> {
                    logger.debug("No game copy of id={} found. Set game copy : {}", id, newCopy);
                    throw new NotFoundException("No game copy of id = " + id + "found.");
                }));
    }


    public GameCopyDto addPublisher(Long copyId, Long publisherId) {
        Publisher publisher = publisherRepository
                .findById(publisherId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Publisher of id=" + publisherId + " found.");
                        }
                );
        return mapper.copyToDto(copyRepository
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
                ));
    }

    public GameCopyDto removePublisher(Long copyId, Long publisherId) {
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
        return mapper.copyToDto(this.gameCopyToReturn);
    }

    public void checkLoanability(Long gameCopyId) {
        String errorMessage = "";
        // find game copy by id
        // if no result throw exception
        GameCopy gc = copyRepository.findById(gameCopyId).get();
        if (gc == null) {
            // todo useless??
            errorMessage = "No game copy of id=" + gameCopyId + " found in database.";
            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }

        // check if game has marker 'loanable'
        // if not throw exception
        if (!gc.isAvailableForLoan()) {
            errorMessage = "Game copy of id=" + gameCopyId + " is set to Not Loanable";
            logger.warn(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    public Page<GameCopyDto> findLoanReadyPage(Pageable pageable) {
        return mapper.pageToPageDto(copyRepository.findPageByLoanability(pageable));
    }

    public List<GameCopyDto> findLoanReady() {
        return mapper.copiesToDto(copyRepository.findByLoanability());
    }

    public List<GameCopyDto> findAll() {
        return mapper.copiesToDto(copyRepository.findAll());
    }
}
