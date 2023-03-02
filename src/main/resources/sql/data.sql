INSERT INTO public.category (id, lower_case_name, name)
VALUES (1, 'réflexion', 'Réflexion'),
       (2, 'stratégie', 'Stratégie'),
       (3, 'puzzle', 'Puzzle'),
       (4, 'gestion', 'Gestion'),
       (5, 'programmation', 'Programmation'),
       (6, 'hazard', 'Hazard'),
       (7, 'cartes', 'Cartes'),
       (8, 'dominos', 'Dominos'),
       (9, 'lettres', 'Lettres'),
       (10, 'associations d''idées', 'Associations d''idées'),
       (11, 'course', 'Course'),
       (12, 'négociation', 'Négociation');
SELECT setval('category_sequence', 12, true);

INSERT INTO public.product_line (id, lower_case_name, name)
VALUES (1, 'catane', 'Catane');
SELECT setval('product_line_sequence', 1, true);

INSERT INTO public.theme(id, lower_case_name, name)
VALUES (1, 'médiéval', 'Médiéval'),
       (2, 'urbanisme', 'Urbanisme'),
       (3, 'espace', 'Espace'),
       (4, 'espionnage', 'Espionnage'),
       (5, 'commerce', 'Commerce'),
       (6, 'fantastique', 'Fantastique'),
       (7, 'horreur', 'Horreur'),
       (8, 'mythologie', 'Mythologie'),
       (9, 'animaux', 'Animaux'),
       (10, 'histoire', 'Histoire'),
       (11, 'enquête', 'Enquête'),
       (12, 'far west', 'Far West');
SELECT setval('theme_sequence', 12, true);

INSERT INTO public.account (id, first_name, last_name, membership_number, renewal_date, username, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (1, 'John', 'Doe', 'f993a834-37ce-40d2-8863-a512813d689b', '2021-07-03', 'John-Doe',
        'Paris', 'France', null, '+0331', '75000', 'foo street', '40', null);
SELECT setval('account_sequence', 1, true);

INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (1, 'Bruno', 'Cathala', 'bruno', 'cathala', 0, 'Marseille', 'France', null, '+0333',
        '13000', 'foo street', '45', null),
       (2, 'Bruno', 'Faduitti', 'bruno', 'faduitti', 0, 'Lyon', 'France', null, '+0332',
        '75000', 'foo street', '32', null),
       (3, 'Mihajlo', 'Dimitrievski', 'mihajlo', 'dimitrievski', 2, 'Dijon', 'France', null,
        '+0334', '75000', 'foo street', '1', null),
       (4, 'Tom', 'Lehmann', 'tom', 'lehmann', 0, null, 'États-Unis', null,
        null, null, null, null, null),
       (5, 'Wei-Hwa', 'Huang', 'wei-hwa', 'huang', 2, null, 'États-Unis', null,
        null, null, null, null, null),
       (6, 'Claus', 'Stephan', 'claus', 'stephan', 2, null, 'États-Unis', null,
        null, null, null, null, null),
       (7, 'Martin', 'Hoffmann', 'martin', 'hoffmann', 2, null, 'États-Unis', null,
        null, null, null, null, null);
SELECT setval('creator_sequence', 7, true);

INSERT INTO public.seller (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                           house_number, website)
VALUES (1, 'joué club tulle', 'Joué Club Tulle', 'Lille', 'France', null, '+0334', '75000',
        'foo street', '1', null),
       (2, 'boutique philibert', 'Boutique Philibert', 'Bordeaux', 'France', null, '+0334',
        '75000', 'foo street', '1', null);
SELECT setval('seller_sequence', 2, true);

INSERT INTO public.publisher (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                              house_number, website)
VALUES (1, 'kosmos', 'Kosmos', 'Rennes', 'France', null, '+0334', '75000', 'foo street',
        '1', null),
       (2, 'asmodee', 'Asmodee', 'Nantes', 'France', null, '+0334', '75000', 'foo street',
        '1', null),
       (3, 'rio grande games', 'Rio Grande Games', 'Rio Rancho', 'États-Unis', null,
        '+0334', '1100', 'bar street',
        '50', null);
SELECT setval('publisher_sequence', 3, true);

INSERT INTO public.game (id, core_rules, description, ending, goal, lower_case_name, max_age,
                         max_number_of_player, min_age, min_month, min_number_of_player, name, nature, play_time,
                         preparation, size, stuff, variant, core_game_id, fk_product_line)
VALUES (1, null,
        'Chaque joueur dirige un monde voué à s''étendre afin de créer l''empire le plus prospère. ', null,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit',
        'race for the galaxy', 0, 4, 10, 0, 2,
        'Race for the Galaxy', 1, '45 minutes', null, 'Petit',
        null, null, null,
        null),
       (2, null,
        'Vous voilà à la tête de colons fraîchement débarqués sur l''île de Catane. Votre but va être d''installer vos ouailles et de faire prospérer vos colonies en construisant des villes et en utilisant au mieux les matières premières qui sont à votre disposition.
Les Colons de Catane est un jeu tactique de placement, de développement et de négociation. Le hasard y est présent et peut à tout moment contrarier vos plans.',
        null,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit',
        'les colons de catane', 0, 4, 8, 0, 2,
        'Les Colons de Catane', 1, '90 minutes', null, 'Petit',
        null, null, null,
        (SELECT id FROM product_line WHERE name = 'Catane'));
SELECT setval('game_sequence', 2, true);

INSERT INTO public.game_copy (id, date_of_purchase, general_state, is_loanable, location, object_code, price,
                              register_date, wear_condition, fk_game, fk_publisher, fk_seller)
VALUES (1, '2018-05-20', 0, true,
        'Étagère jeu famille', '00050', 40.00, '2021-07-03', 'Bon état',
        (SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM publisher WHERE name = 'Kosmos'),
        (SELECT id FROM seller WHERE name = 'Joué Club Tulle'));
SELECT setval('game_copy_sequence', 1, true);

INSERT INTO public.loan (id, is_closed, loan_end_time, loan_start_time, fk_account, fk_game_copy)
VALUES (1, true, '2020-08-16', '2020-07-16',
        (SELECT id FROM account WHERE first_name = 'John' AND last_name = 'Doe'),
        (SELECT id FROM game_copy WHERE object_code = '00050'));
SELECT setval('loan_sequence', 1, true);

INSERT INTO public.game_category (fk_game, fk_category)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'), (SELECT id FROM category WHERE name = 'Réflexion')),
       ((SELECT id FROM game WHERE name = 'Les Colons de Catane'), (SELECT id FROM category WHERE name = 'Stratégie')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'), (SELECT id FROM category WHERE name = 'Stratégie')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'), (SELECT id FROM category WHERE name = 'Cartes'));


INSERT INTO public.game_creator (fk_game, fk_creator)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM creator WHERE first_name = 'Bruno' AND last_name = 'Cathala')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Tom' AND last_name = 'Lehmann')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Wei-Hwa' AND last_name = 'Huang')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Claus' AND last_name = 'Stephan')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Martin' AND last_name = 'Hoffmann'));

INSERT INTO public.game_theme (fk_game, fk_theme)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'), (SELECT id FROM theme WHERE name = 'Médiéval')),
       ((SELECT id FROM game WHERE name = 'Race for the Galaxy'), (SELECT id FROM theme WHERE name = 'Espace'));
