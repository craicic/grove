package org.motoc.gamelibrary;


import org.motoc.gamelibrary.business.ImageService;
import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.model.enumeration.CreatorRole;
import org.motoc.gamelibrary.model.enumeration.GameNatureEnum;
import org.motoc.gamelibrary.model.enumeration.GeneralStateEnum;
import org.motoc.gamelibrary.repository.jpa.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * It's methods run is executed on startup. It fill the database with demo data.
 */
@Component
public class CommandLineStartupRunner
        implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineStartupRunner.class);

    // Repositories
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ContactRepository contactRepository;
    private final CreatorRepository creatorRepository;
    private final GameRepository gameRepository;
    private final GameCopyRepository gameCopyRepository;
    private final ImageRepository imageRepository;
    private final LoanRepository loanRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final ProductLineRepository productLineRepository;
    private final PublisherRepository publisherRepository;
    private final SellerRepository sellerRepository;
    private final ThemeRepository themeRepository;

    // Fields
    private final Account demoAccountA;
    private final Contact demoContactA;
    private final Contact demoContactB;
    private final Contact demoContactC;
    private final Contact demoContactD;
    private final Contact demoContactE;
    private final Contact demoContactF;
    private final Contact demoContactG;
    private final Contact demoContactH;
    private final ProductLine demoProductLineA;
    private final Publisher demoPublisherA;
    private final Publisher demoPublisherB;
    private final Creator demoCreatorA;
    private final Creator demoCreatorB;
    private final Creator demoCreatorC;
    private final Theme demoThemeA;
    private final Theme demoThemeB;
    private final Theme demoThemeC;
    private final Theme demoThemeD;
    private final Theme demoThemeE;
    private final Theme demoThemeF;
    private final Theme demoThemeG;
    private final Theme demoThemeH;
    private final Theme demoThemeI;
    private final Theme demoThemeJ;
    private final Theme demoThemeK;
    private final Theme demoThemeL;
    private final Category demoCategoryA;
    private final Category demoCategoryB;
    private final Category demoCategoryC;
    private final Category demoCategoryD;
    private final Category demoCategoryE;
    private final Category demoCategoryF;
    private final Category demoCategoryG;
    private final Category demoCategoryH;
    private final Category demoCategoryI;
    private final Category demoCategoryJ;
    private final Category demoCategoryK;
    private final Category demoCategoryL;
    private final Seller demoSellerA;
    private final Seller demoSellerB;
    private final Game demoGameA;
    private final GameCopy demoGameCopyA;
    private final LoanStatus demoLoanStatusA;
    private final LoanStatus demoLoanStatusB;
    private final LoanStatus demoLoanStatusC;
    private final ImageService imageService;

    @Autowired
    public CommandLineStartupRunner(AccountRepository accountRepository,
                                    CategoryRepository categoryRepository,
                                    ContactRepository contactRepository,
                                    CreatorRepository creatorRepository,
                                    GameRepository gameRepository,
                                    GameCopyRepository gameCopyRepository,
                                    ImageRepository imageRepository, LoanRepository loanRepository,
                                    LoanStatusRepository loanStatusRepository,
                                    ProductLineRepository productLineRepository,
                                    PublisherRepository publisherRepository,
                                    SellerRepository sellerRepository,
                                    ThemeRepository themeRepository,
                                    ImageService imageService) {
        logger.warn("Starting CommandLineRunner");
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.creatorRepository = creatorRepository;
        this.gameRepository = gameRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.imageRepository = imageRepository;
        this.loanRepository = loanRepository;
        this.loanStatusRepository = loanStatusRepository;
        this.productLineRepository = productLineRepository;
        this.publisherRepository = publisherRepository;
        this.sellerRepository = sellerRepository;
        this.themeRepository = themeRepository;
        this.imageService = imageService;

        this.demoAccountA = new Account();
        this.demoContactA = new Contact();
        this.demoContactB = new Contact();
        this.demoContactC = new Contact();
        this.demoContactD = new Contact();
        this.demoContactE = new Contact();
        this.demoContactF = new Contact();
        this.demoContactG = new Contact();
        this.demoContactH = new Contact();
        this.demoProductLineA = new ProductLine();
        this.demoPublisherA = new Publisher();
        this.demoPublisherB = new Publisher();
        this.demoCreatorA = new Creator();
        this.demoCreatorB = new Creator();
        this.demoCreatorC = new Creator();
        this.demoThemeA = new Theme();
        this.demoThemeB = new Theme();
        this.demoThemeC = new Theme();
        this.demoThemeD = new Theme();
        this.demoThemeE = new Theme();
        this.demoThemeF = new Theme();
        this.demoThemeG = new Theme();
        this.demoThemeH = new Theme();
        this.demoThemeI = new Theme();
        this.demoThemeJ = new Theme();
        this.demoThemeK = new Theme();
        this.demoThemeL = new Theme();
        this.demoCategoryA = new Category();
        this.demoCategoryB = new Category();
        this.demoCategoryC = new Category();
        this.demoCategoryD = new Category();
        this.demoCategoryE = new Category();
        this.demoCategoryF = new Category();
        this.demoCategoryG = new Category();
        this.demoCategoryH = new Category();
        this.demoCategoryI = new Category();
        this.demoCategoryJ = new Category();
        this.demoCategoryK = new Category();
        this.demoCategoryL = new Category();
        this.demoSellerA = new Seller();
        this.demoSellerB = new Seller();
        this.demoGameA = new Game();
        this.demoGameCopyA = new GameCopy();
        this.demoLoanStatusA = new LoanStatus();
        this.demoLoanStatusB = new LoanStatus();
        this.demoLoanStatusC = new LoanStatus();
    }

    @Override
    public void run(String... args) throws Exception {

//        this.fillCategories();
//        this.fillContacts();
//        this.fillLoanStatus();
//        this.fillProductLines();
//        this.fillThemes();

//        this.fillAccounts();
//        this.fillCreators();
//        this.fillSellers();
//        this.fillPublishers();

//        this.fillGames();
//        this.fillGameCopies();
//        this.fillLoans();

        this.fillImages();

    }

    private void fillAccounts() {

        demoAccountA.setMembershipNumber("0015");
        demoAccountA.setRenewalDate(LocalDate.of(2019, 6, 15));
        demoAccountA.setContact(demoContactA);
        demoAccountA.setUsername("John-Doe");
        demoAccountA.setFirstName("John");
        demoAccountA.setLastName("Doe");
        accountRepository.save(demoAccountA);
        accountRepository.flush();
    }

    private void fillCategories() {
        demoCategoryB.setName("Stratégie");
        demoCategoryC.setName("Puzzle");
        demoCategoryA.setName("Réflexion");
        demoCategoryD.setName("Gestion");
        demoCategoryE.setName("Programmation");
        demoCategoryF.setName("Hazard");
        demoCategoryG.setName("Cartes");
        demoCategoryH.setName("Dominos");
        demoCategoryI.setName("Lettres");
        demoCategoryJ.setName("Associations d'idées");
        demoCategoryK.setName("Course");
        demoCategoryL.setName("Négociation");

        categoryRepository.save(demoCategoryA);
        categoryRepository.save(demoCategoryB);
        categoryRepository.save(demoCategoryC);
        categoryRepository.save(demoCategoryD);
        categoryRepository.save(demoCategoryE);
        categoryRepository.save(demoCategoryF);
        categoryRepository.save(demoCategoryG);
        categoryRepository.save(demoCategoryH);
        categoryRepository.save(demoCategoryI);
        categoryRepository.save(demoCategoryJ);
        categoryRepository.save(demoCategoryK);
        categoryRepository.save(demoCategoryL);
        categoryRepository.flush();

    }

    private void fillContacts() {
        demoContactA.setCountry("France");
        demoContactA.setCity("Paris");
        demoContactA.setPhoneNumber("+0331");
        demoContactA.setPostalCode("75000");
        demoContactA.setStreet("foo street");
        demoContactA.setStreetNumber("40");


        demoContactB.setCountry("France");
        demoContactB.setCity("Marseille");
        demoContactB.setPhoneNumber("+0333");
        demoContactB.setPostalCode("13000");
        demoContactB.setStreet("foo street");
        demoContactB.setStreetNumber("45");

        demoContactC.setCountry("France");
        demoContactC.setCity("Lyon");
        demoContactC.setPhoneNumber("+0332");
        demoContactC.setPostalCode("75000");
        demoContactC.setStreet("foo street");
        demoContactC.setStreetNumber("32");

        demoContactD.setCountry("France");
        demoContactD.setCity("Dijon");
        demoContactD.setPhoneNumber("+0334");
        demoContactD.setPostalCode("75000");
        demoContactD.setStreet("foo street");
        demoContactD.setStreetNumber("1");

        demoContactE.setCountry("France");
        demoContactE.setCity("Lille");
        demoContactE.setPhoneNumber("+0334");
        demoContactE.setPostalCode("75000");
        demoContactE.setStreet("foo street");
        demoContactE.setStreetNumber("1");

        demoContactF.setCountry("France");
        demoContactF.setCity("Bordeaux");
        demoContactF.setPhoneNumber("+0334");
        demoContactF.setPostalCode("75000");
        demoContactF.setStreet("foo street");
        demoContactF.setStreetNumber("1");

        demoContactG.setCountry("France");
        demoContactG.setCity("Rennes");
        demoContactG.setPhoneNumber("+0334");
        demoContactG.setPostalCode("75000");
        demoContactG.setStreet("foo street");
        demoContactG.setStreetNumber("1");

        demoContactH.setCountry("France");
        demoContactH.setCity("Nantes");
        demoContactH.setPhoneNumber("+0334");
        demoContactH.setPostalCode("75000");
        demoContactH.setStreet("foo street");
        demoContactH.setStreetNumber("1");

        contactRepository.save(demoContactA);
        contactRepository.save(demoContactB);
        contactRepository.save(demoContactC);
        contactRepository.save(demoContactD);
        contactRepository.save(demoContactE);
        contactRepository.save(demoContactF);
        contactRepository.save(demoContactG);
        contactRepository.save(demoContactH);
        contactRepository.flush();
    }

    private void fillCreators() {
        demoCreatorA.setFirstName("Bruno");
        demoCreatorA.setLastName("Cathala");
        demoCreatorA.setRole(CreatorRole.AUTHOR);
        demoCreatorA.addContact(demoContactB);

        demoCreatorB.setFirstName("Bruno");
        demoCreatorB.setLastName("Faduitti");
        demoCreatorB.setRole(CreatorRole.AUTHOR);
        demoCreatorB.addContact(demoContactE);

        demoCreatorC.setFirstName("Mihajlo");
        demoCreatorC.setLastName("Dimitrievski");
        demoCreatorC.setRole(CreatorRole.ILLUSTRATOR);
        demoCreatorC.addContact(demoContactF);

        creatorRepository.save(demoCreatorA);
        creatorRepository.save(demoCreatorB);
        creatorRepository.save(demoCreatorC);
        creatorRepository.flush();
    }

    private void fillGames() {
        demoGameA.setName("Les Colons de Catane");
        demoGameA.setDescription("Vous voilà à la tête de colons fraîchement débarqués sur l'île de Catane. Votre " +
                "but va être d'installer vos ouailles et de faire prospérer vos colonies en construisant des villes " +
                "et en utilisant au mieux les matières premières qui sont à votre disposition.\n" +
                "Les Colons de Catane est un jeu tactique de placement, de développement et de négociation. Le hasard " +
                "y est présent et peut à tout moment contrarier vos plans.");
        demoGameA.setPlayTime("90 minutes");
        demoGameA.setMinNumberOfPlayer((short) 2);
        demoGameA.setMaxNumberOfPlayer((short) 4);
        demoGameA.setMinAge((short) 8);
        demoGameA.setProductLine(demoProductLineA);


        demoGameA.setNature(GameNatureEnum.BOARD_GAME);
        demoGameA.setSize("Petit");
        demoGameA.setStuff("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit");

        demoGameA.setGoal("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit");
        demoGameA.setCoreRules("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id " +
                "blandit lobortis, lectus sem porta ipsum, in vestibulum eros magna vel dui. Class aptent taciti " +
                "sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut scelerisque est et varius " +
                "molestie. Quisque nec odio eu mauris euismod mollis in ut ligula. Nulla pellentesque iaculis interdum." +
                " Curabitur felis magna, placerat at rutrum ac, auctor id metus. Cras tempor porttitor ex, ut efficitur " +
                "ex ullamcorper eget. Cras fringilla urna vel aliquam consectetur.");
        demoGameA.setEnding("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit");

        Set<Creator> creatorsA = new HashSet<>();
        creatorsA.add(demoCreatorA);
        demoGameA.setCreators(creatorsA);

        Set<Theme> themesA = new HashSet<>();
        themesA.add(demoThemeA);
        demoGameA.setThemes(themesA);

        Set<Category> categoriesA = new HashSet<>();
        categoriesA.add(demoCategoryA);
        categoriesA.add(demoCategoryB);
        demoGameA.setCategories(categoriesA);

        gameRepository.save(demoGameA);
        gameRepository.flush();
    }

    private void fillGameCopies() {
        demoGameCopyA.setObjectCode("00050");
        demoGameCopyA.setPrice(BigDecimal.valueOf(40));
        demoGameCopyA.setLocation("Étagère jeu famille");
        demoGameCopyA.setDateOfPurchase(LocalDate.of(2018, 5, 20));
        demoGameCopyA.setRegisterDate(LocalDate.now());
        demoGameCopyA.setWearCondition("Bon état");
        demoGameCopyA.setGeneralState(GeneralStateEnum.IN_ACTIVITY);
        demoGameCopyA.setLoanable(true);
        demoGameCopyA.setSeller(demoSellerA);
        demoGameCopyA.setGame(demoGameA);
        demoGameCopyA.setPublisher(demoPublisherA);
        gameCopyRepository.save(demoGameCopyA);
    }


    public void fillImages() throws IOException {
        File fileA = new File("src/main/resources/static/catane.jpg");
        BufferedImage imageA = ImageIO.read(fileA.getAbsoluteFile());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(imageA, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());


        imageService.saveThenAttachToGame(is, 45L);
        os.close();
        is.close();

//        File fileB = new File("src/main/resources/static/catane2.jpg");
//        BufferedImage imageB = ImageIO.read(fileB);
//        ByteArrayOutputStream osB = new ByteArrayOutputStream();
//        ImageIO.write(imageB, "png", osB);
//        InputStream isB = new ByteArrayInputStream(osB.toByteArray());
//
//        imageService.saveThenAttachToGame(isB, 45L);
//        osB.close();
//        isB.close();
    }


    private void fillLoans() {
        Loan demoLoanA = new Loan();

        demoLoanA.setLoanStartTime(LocalDate.of(2020, 7, 16));
        demoLoanA.setLoanEndTime(LocalDate.of(2020, 8, 16));
        demoLoanA.setClosed(true);
        demoLoanA.setGameCopy(demoGameCopyA);
        demoLoanA.setAccount(demoAccountA);
        loanRepository.save(demoLoanA);
        loanRepository.flush();

    }

    private void fillLoanStatus() {
        demoLoanStatusA.setTag("Proposé");
        demoLoanStatusA.setDescription("L'adhérent a émis une proposition d'emprunt pour le jeu");
        demoLoanStatusB.setTag("Validé");
        demoLoanStatusB.setDescription("L'équipe a validé l'emprunt");
        demoLoanStatusC.setTag("En cours");
        demoLoanStatusC.setDescription("L'objet a été emprunté par l'adhérent");
        loanStatusRepository.save(demoLoanStatusA);
        loanStatusRepository.save(demoLoanStatusB);
        loanStatusRepository.save(demoLoanStatusC);
        loanStatusRepository.flush();
    }

    private void fillProductLines() {
        demoProductLineA.setName("Catane");

        productLineRepository.save(demoProductLineA);
        productLineRepository.flush();
    }

    private void fillPublishers() {
        demoPublisherA.setName("Kosmos");
        demoPublisherA.setContact(demoContactD);

        demoPublisherB.setName("Asmodee");
        demoPublisherB.setContact(demoContactG);

        publisherRepository.save(demoPublisherA);
        publisherRepository.save(demoPublisherB);
        publisherRepository.flush();
    }

    private void fillSellers() {
        demoSellerA.setName("Joué Club Tulle");
        demoSellerA.setContact(demoContactC);

        demoSellerB.setName("Boutique Philibert");
        demoSellerB.setContact(demoContactH);

        sellerRepository.save(demoSellerA);
        sellerRepository.save(demoSellerB);
        sellerRepository.flush();
    }

    private void fillThemes() {
        demoThemeA.setName("Médiéval");
        demoThemeB.setName("Urbanisme");
        demoThemeC.setName("Espace");
        demoThemeD.setName("Espionnage");
        demoThemeE.setName("Commerce");
        demoThemeF.setName("Fantastique");
        demoThemeG.setName("Horreur");
        demoThemeH.setName("Mythologie");
        demoThemeI.setName("Animaux");
        demoThemeJ.setName("Histoire");
        demoThemeK.setName("Enquête");
        demoThemeL.setName("Far West");

        themeRepository.save(demoThemeA);
        themeRepository.save(demoThemeB);
        themeRepository.save(demoThemeC);
        themeRepository.save(demoThemeD);
        themeRepository.save(demoThemeE);
        themeRepository.save(demoThemeF);
        themeRepository.save(demoThemeG);
        themeRepository.save(demoThemeH);
        themeRepository.save(demoThemeI);
        themeRepository.save(demoThemeJ);
        themeRepository.save(demoThemeK);
        themeRepository.save(demoThemeL);
        themeRepository.flush();
    }
}
