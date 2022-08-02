INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'réflexion', 'Réflexion');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'stratégie', 'Stratégie');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'puzzle', 'Puzzle');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'gestion', 'Gestion');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'programmation', 'Programmation');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'hazard', 'Hazard');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'cartes', 'Cartes');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'dominos', 'Dominos');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'lettres', 'Lettres');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'associations d''idées', 'Associations d''idées');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'course', 'Course');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('category_sequence'), 'négociation', 'Négociation');



INSERT INTO public.product_line (id, lower_case_name, name)
VALUES (nextval('product_line_sequence'), 'catane', 'Catane');

INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'médiéval', 'Médiéval');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'urbanisme', 'Urbanisme');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'espace', 'Espace');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'espionnage', 'Espionnage');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'commerce', 'Commerce');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'fantastique', 'Fantastique');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'horreur', 'Horreur');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'mythologie', 'Mythologie');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'animaux', 'Animaux');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'histoire', 'Histoire');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'enquête', 'Enquête');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('theme_sequence'), 'far west', 'Far West');

INSERT INTO public.account (id, first_name, last_name, membership_number, renewal_date, username, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (nextval('account_sequence'), 'John', 'Doe', 'f993a834-37ce-40d2-8863-a512813d689b', '2021-07-03', 'John-Doe',
        'Paris', 'France', null, '+0331', '75000', 'foo street', '40', null);


INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (nextval('creator_sequence'), 'Bruno', 'Cathala', 'bruno', 'cathala', 0, 'Marseille', 'France', null, '+0333',
        '13000', 'foo street', '45', null);
INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (nextval('creator_sequence'), 'Bruno', 'Faduitti', 'bruno', 'faduitti', 0, 'Lyon', 'France', null, '+0332',
        '75000', 'foo street', '32', null);
INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, city, country,
                            mail_address, phone_number, post_code, street, house_number, website)
VALUES (nextval('creator_sequence'), 'Mihajlo', 'Dimitrievski', 'mihajlo', 'dimitrievski', 2, 'Dijon', 'France', null,
        '+0334', '75000', 'foo street', '1', null);

INSERT INTO public.seller (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                           house_number, website)
VALUES (nextval('seller_sequence'), 'joué club tulle', 'Joué Club Tulle', 'Lille', 'France', null, '+0334', '75000',
        'foo street', '1', null);
INSERT INTO public.seller (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                           house_number, website)
VALUES (nextval('seller_sequence'), 'boutique philibert', 'Boutique Philibert', 'Bordeaux', 'France', null, '+0334',
        '75000', 'foo street', '1', null);

INSERT INTO public.publisher (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                              house_number, website)
VALUES (nextval('publisher_sequence'), 'kosmos', 'Kosmos', 'Rennes', 'France', null, '+0334', '75000', 'foo street',
        '1', null);
INSERT INTO public.publisher (id, lower_case_name, name, city, country, mail_address, phone_number, post_code, street,
                              house_number, website)
VALUES (nextval('publisher_sequence'), 'asmodee', 'Asmodee', 'Nantes', 'France', null, '+0334', '75000', 'foo street',
        '1', null);


INSERT INTO public.game (id, core_rules, description, edition_number, ending, goal, lower_case_name, max_age,
                         max_number_of_player, min_age, min_month, min_number_of_player, name, nature, play_time,
                         preparation, size, stuff, variant, core_game_id, fk_product_line)
VALUES (nextval('game_sequence'), null,
        'Vous voilà à la tête de colons fraîchement débarqués sur l''île de Catane. Votre but va être d''installer vos ouailles et de faire prospérer vos colonies en construisant des villes et en utilisant au mieux les matières premières qui sont à votre disposition.
Les Colons de Catane est un jeu tactique de placement, de développement et de négociation. Le hasard y est présent et peut à tout moment contrarier vos plans.',
        null, null, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit',
        'les colons de catane', 0, 4, 8, 0, 2,
        'Les Colons de Catane', 1, '90 minutes', null, 'Petit',
        null, null, null,
        (SELECT id FROM product_line WHERE name = 'Catane'));

INSERT INTO public.game_copy (id, date_of_purchase, general_state, is_loanable, location, object_code, price,
                              register_date, wear_condition, fk_game, fk_publisher, fk_seller)
VALUES (nextval('game_copy_sequence'), '2018-05-20', 0, true,
        'Étagère jeu famille', '00050', 40.00, '2021-07-03', 'Bon état',
        (SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM publisher WHERE name = 'Kosmos'),
        (SELECT id FROM seller WHERE name = 'Joué Club Tulle'));

INSERT INTO public.loan (id, is_closed, loan_end_time, loan_start_time, fk_account, fk_game_copy)
VALUES (nextval('loan_sequence'), true, '2020-08-16', '2020-07-16',
        (SELECT id FROM account WHERE first_name = 'John' AND last_name = 'Doe'),
        (SELECT id FROM game_copy WHERE object_code = '00050'));

INSERT INTO public.game_category (fk_game, fk_category)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM category WHERE name = 'Réflexion'));

INSERT INTO public.game_category (fk_game, fk_category)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM category WHERE name = 'Stratégie'));

INSERT INTO public.game_creator (fk_game, fk_creator)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM creator WHERE first_name = 'Bruno' AND last_name = 'Cathala'));

INSERT INTO public.game_theme (fk_game, fk_theme)
VALUES ((SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM theme WHERE name = 'Médiéval'));

