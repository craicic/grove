package org.motoc.gamelibrary;


import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.model.enumeration.CreatorRole;
import org.motoc.gamelibrary.model.enumeration.GeneralStateEnum;
import org.motoc.gamelibrary.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * It's methods run is executed on startup. It fill the database with demo data.
 *
 * @author RouzicJ
 */
@Component
public class CommandLineStartupRunner implements CommandLineRunner {


    // Repositories
    private final AccountRepository accountRepository;
    private final ArticleRepository articleRepository;
    private final ArticleAuthorRepository articleAuthorRepository;
    private final CategoryRepository categoryRepository;
    private final ContactRepository contactRepository;
    private final CreatorRepository creatorRepository;
    private final GameRepository gameRepository;
    private final GameCopyRepository gameCopyRepository;
    private final ImageRepository imageRepository;
    private final KeywordRepository keywordRepository;
    private final LoanRepository loanRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final ProductLineRepository productLineRepository;
    private final PublisherRepository publisherRepository;
    private final SellerRepository sellerRepository;
    private final ThemeRepository themeRepository;

    // Fields
    private final Contact demoContactA;
    private final Contact demoContactB;
    private final Contact demoContactC;
    private final Contact demoContactD;
    private final Contact demoContactE;
    private final Contact demoContactF;
    private final ArticleAuthor demoArticleAuthorA;
    private final ArticleAuthor demoArticleAuthorB;
    private final Image demoImageA;
    private final Image demoImageB;
    private final Keyword demoKeywordA;
    private final Keyword demoKeywordB;
    private final Keyword demoKeywordC;
    private final ProductLine demoProductLineA;
    private final Publisher demoPublisherA;
    private final Creator demoCreatorA;
    private final Creator demoCreatorB;
    private final Creator demoCreatorC;
    private final Theme demoThemeA;
    private final Theme demoThemeB;
    private final Theme demoThemeC;
    private final Category demoCategoryA;
    private final Category demoCategoryB;
    private final Category demoCategoryC;
    private final Seller demoSellerA;
    private final Game demoGameA;
    private final GameCopy demoGameCopyA;
    private final LoanStatus demoLoanStatusA;
    private final LoanStatus demoLoanStatusB;
    private final LoanStatus demoLoanStatusC;

    @Autowired
    public CommandLineStartupRunner(AccountRepository accountRepository, ArticleRepository articleRepository,
                                    ArticleAuthorRepository articleAuthorRepository,
                                    CategoryRepository categoryRepository,
                                    ContactRepository contactRepository,
                                    CreatorRepository creatorRepository,
                                    GameRepository gameRepository,
                                    GameCopyRepository gameCopyRepository,
                                    ImageRepository imageRepository,
                                    KeywordRepository keywordRepository, LoanRepository loanRepository,
                                    LoanStatusRepository loanStatusRepository,
                                    ProductLineRepository productLineRepository,
                                    PublisherRepository publisherRepository,
                                    SellerRepository sellerRepository,
                                    ThemeRepository themeRepository) {
        this.accountRepository = accountRepository;
        this.articleRepository = articleRepository;
        this.articleAuthorRepository = articleAuthorRepository;
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.creatorRepository = creatorRepository;
        this.gameRepository = gameRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.imageRepository = imageRepository;
        this.keywordRepository = keywordRepository;
        this.loanRepository = loanRepository;
        this.loanStatusRepository = loanStatusRepository;
        this.productLineRepository = productLineRepository;
        this.publisherRepository = publisherRepository;
        this.sellerRepository = sellerRepository;
        this.themeRepository = themeRepository;

        this.demoContactA = new Contact();
        this.demoContactB = new Contact();
        this.demoContactC = new Contact();
        this.demoContactD = new Contact();
        this.demoContactE = new Contact();
        this.demoContactF = new Contact();
        this.demoArticleAuthorA = new ArticleAuthor();
        this.demoArticleAuthorB = new ArticleAuthor();
        this.demoImageA = new Image();
        this.demoImageB = new Image();
        this.demoKeywordA = new Keyword();
        this.demoKeywordB = new Keyword();
        this.demoKeywordC = new Keyword();
        this.demoProductLineA = new ProductLine();
        this.demoPublisherA = new Publisher();
        this.demoCreatorA = new Creator();
        this.demoCreatorB = new Creator();
        this.demoCreatorC = new Creator();
        this.demoThemeA = new Theme();
        this.demoThemeB = new Theme();
        this.demoThemeC = new Theme();
        this.demoCategoryA = new Category();
        this.demoCategoryB = new Category();
        this.demoCategoryC = new Category();
        this.demoSellerA = new Seller();
        this.demoGameA = new Game();
        this.demoGameCopyA = new GameCopy();
        this.demoLoanStatusA = new LoanStatus();
        this.demoLoanStatusB = new LoanStatus();
        this.demoLoanStatusC = new LoanStatus();
    }

    @Override
    public void run(String... args) throws Exception {

        this.fillArticleAuthor();
        this.fillCategory();
        this.fillContact();
        this.fillImage();
        this.fillKeyword();
        this.fillLoanStatus();
        this.fillProductLine();
        this.fillTheme();

        this.fillAccount();
        this.fillCreator();
        this.fillSeller();
        this.fillPublisher();

        this.fillArticle();
        this.fillGame();
        this.fillGameCopy();
        this.fillLoan();

    }

    private void fillAccount() {
        Account demoAccountA = new Account();
        demoAccountA.setMembershipNumber("0015");
        demoAccountA.setRenewalDate(LocalDate.of(2019, 6, 15));
        demoAccountA.setContact(demoContactA);
        demoAccountA.setUserUuid("REPLACE_THIS_UUID");
        accountRepository.save(demoAccountA);
        accountRepository.flush();
    }

    private void fillArticle() {
        Article demoArticleA = new Article();
        Article demoArticleB = new Article();

        demoArticleA.setLastEditTime(LocalDateTime.now());
        demoArticleA.setPublicationTime(LocalDateTime.of(2019, 12, 20, 10, 30));
        demoArticleA.setHtmlContent("<h1>Article A content<h1/><p>Mauris nec velit eu turpis laoreet viverra. Donec feugiat ante ullamcorper tristique feugiat. Aliquam erat volutpat. Vivamus fermentum metus est, a pellentesque lectus aliquet in.</p>");
        demoArticleA.setShortDescription("This is article A... about tournament");
        demoArticleA.setArticleAuthor(demoArticleAuthorA);

        Set<Image> imagesA = new HashSet<>();
        imagesA.add(demoImageA);
        demoArticleA.setImages(imagesA);

        Set<Keyword> keywordsA = new HashSet<>();
        keywordsA.add(demoKeywordA);
        keywordsA.add(demoKeywordC);
        demoArticleA.setKeywords(keywordsA);

        articleRepository.save(demoArticleA);
        articleRepository.flush();

        demoArticleB.setLastEditTime(LocalDateTime.now());
        demoArticleB.setPublicationTime(LocalDateTime.of(2020, 3, 8, 15, 0));
        demoArticleB.setHtmlContent("<h1>Article B content<h1/><p>Nullam nec ipsum nisi. Aenean ullamcorper, leo eget luctus fermentum, dolor est varius nunc, vel varius massa purus ac urna. In metus orci, dapibus in malesuada nec, posuere vitae augue.</p>");
        demoArticleB.setShortDescription("This is article B... about formation");
        demoArticleB.setArticleAuthor(demoArticleAuthorB);

        Set<Image> imagesB = new HashSet<>();
        imagesB.add(demoImageB);
        demoArticleB.setImages(imagesB);

        Set<Keyword> keywordsB = new HashSet<>();
        keywordsB.add(demoKeywordB);
        demoArticleB.setKeywords(keywordsB);

        articleRepository.save(demoArticleB);
        articleRepository.flush();
    }

    private void fillArticleAuthor() {

        demoArticleAuthorA.setUserUuid("REPLACE_THIS_UUID_1");
        demoArticleAuthorB.setUserUuid("REPLACE_THIS_UUID_2");

        articleAuthorRepository.save(demoArticleAuthorA);
        articleAuthorRepository.save(demoArticleAuthorB);
        articleAuthorRepository.flush();

    }

    private void fillCategory() {
        demoCategoryB.setName("Stratégie");
        demoCategoryC.setName("Puzzle");
        demoCategoryA.setName("Réflexion");
        demoCategoryB.setParent(demoCategoryA);
        demoCategoryC.setParent(demoCategoryA);

        categoryRepository.save(demoCategoryA);
        categoryRepository.flush();
        categoryRepository.save(demoCategoryB);
        categoryRepository.flush();
        categoryRepository.save(demoCategoryC);
        categoryRepository.flush();

    }

    private void fillContact() {
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

        contactRepository.save(demoContactA);
        contactRepository.save(demoContactB);
        contactRepository.save(demoContactC);
        contactRepository.save(demoContactD);
        contactRepository.save(demoContactE);
        contactRepository.save(demoContactF);
        contactRepository.flush();
    }

    private void fillCreator() {
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

    private void fillGame() {
        demoGameA.setName("Les Colons de Catane");
        demoGameA.setDescription("Un jeu de territoire");
        demoGameA.setPlayTime("90");
        demoGameA.setMinNumberOfPlayer((short) 3);
        demoGameA.setMinNumberOfPlayer((short) 4);
        demoGameA.setMinAge((short) 8);
        demoGameA.setProductLine(demoProductLineA);
        demoGameA.setPublisher(demoPublisherA);

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

    private void fillGameCopy() {
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

        gameCopyRepository.save(demoGameCopyA);
    }

    private void fillImage() {
        demoImageA.setFilePath("\\user\\imageA.jpg");
        demoImageB.setFilePath("\\user\\imageB.jpg");

        imageRepository.save(demoImageA);
        imageRepository.save(demoImageB);
        imageRepository.flush();
    }

    private void fillKeyword() {
        demoKeywordA.setTag("Soirée jeux");
        demoKeywordB.setTag("Formation");
        demoKeywordC.setTag("Tournoi");

        keywordRepository.save(demoKeywordA);
        keywordRepository.save(demoKeywordB);
        keywordRepository.save(demoKeywordC);
        keywordRepository.flush();
    }

    private void fillLoan() {
        Loan demoLoanA = new Loan();

        demoLoanA.setUserUuid("REPLACE_THIS_UUID");
        demoLoanA.setLoanStartTime(LocalDateTime.of(2020, 7, 16, 9, 0));
        demoLoanA.setLoanEndTime(LocalDateTime.of(2020, 7, 16, 15, 0));
        demoLoanA.setLoanStatus(demoLoanStatusB);
        demoLoanA.setGameCopy(demoGameCopyA);

        loanRepository.save(demoLoanA);
        loanRepository.flush();

    }

    private void fillLoanStatus() {
        demoLoanStatusA.setTag("Proposé");
        demoLoanStatusA.setDescription("L'adhérent a emis une proposition d'emprunt pour le jeu");
        demoLoanStatusB.setTag("Validé");
        demoLoanStatusB.setDescription("L'équipe a validé l'emprunt");
        demoLoanStatusC.setTag("En cours");
        demoLoanStatusC.setDescription("L'objet a été emprunté par l'adhérent");

        loanStatusRepository.save(demoLoanStatusA);
        loanStatusRepository.save(demoLoanStatusB);
        loanStatusRepository.save(demoLoanStatusC);
        loanStatusRepository.flush();
    }

    private void fillProductLine() {
        demoProductLineA.setName("Catane");

        productLineRepository.save(demoProductLineA);
        productLineRepository.flush();
    }

    private void fillPublisher() {
        demoPublisherA.setName("Kosmos");
        demoPublisherA.setContact(demoContactD);

        publisherRepository.save(demoPublisherA);
        publisherRepository.flush();
    }

    private void fillSeller() {
        demoSellerA.setName("Joué Club Tulle");
        demoSellerA.setContact(demoContactC);

        sellerRepository.save(demoSellerA);
        sellerRepository.flush();
    }

    private void fillTheme() {
        demoThemeA.setName("Médiéval");
        demoThemeB.setName("Urbanisme");
        demoThemeC.setName("Espace");

        themeRepository.save(demoThemeA);
        themeRepository.save(demoThemeB);
        themeRepository.save(demoThemeC);
        themeRepository.flush();
    }


}
