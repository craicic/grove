INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'réflexion', 'Réflexion');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'stratégie', 'Stratégie');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'puzzle', 'Puzzle');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'gestion', 'Gestion');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'programmation', 'Programmation');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'hazard', 'Hazard');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'cartes', 'Cartes');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'dominos', 'Dominos');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'lettres', 'Lettres');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'associations d''idées', 'Associations d''idées');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'course', 'Course');
INSERT INTO public.category (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'négociation', 'Négociation');

INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Paris', 'France', null, '+0331', '75000', 'foo street', '40', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Marseille', 'France', null, '+0333', '13000', 'foo street', '45', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Lyon', 'France', null, '+0332', '75000', 'foo street', '32', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Dijon', 'France', null, '+0334', '75000', 'foo street', '1', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Lille', 'France', null, '+0334', '75000', 'foo street', '1', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Bordeaux', 'France', null, '+0334', '75000', 'foo street', '1', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Rennes', 'France', null, '+0334', '75000', 'foo street', '1', null);
INSERT INTO public.contact (id, city, country, mail_address, phone_number, postal_code, street, street_number, website)
VALUES (nextval('hibernate_sequence'), 'Nantes', 'France', null, '+0334', '75000', 'foo street', '1', null);

INSERT INTO public.product_line (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'catane', 'Catane');

INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'médiéval', 'Médiéval');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'urbanisme', 'Urbanisme');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'espace', 'Espace');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'espionnage', 'Espionnage');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'commerce', 'Commerce');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'fantastique', 'Fantastique');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'horreur', 'Horreur');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'mythologie', 'Mythologie');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'animaux', 'Animaux');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'histoire', 'Histoire');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'enquête', 'Enquête');
INSERT INTO public.theme (id, lower_case_name, name)
VALUES (nextval('hibernate_sequence'), 'far west', 'Far West');

INSERT INTO public.account (id, first_name, last_name, membership_number, renewal_date, username, fk_contact)
VALUES (nextval('hibernate_sequence'), 'John', 'Doe', 'f993a834-37ce-40d2-8863-a512813d689b', '2021-07-03', 'John-Doe',
        13);


INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, fk_contact)
VALUES (nextval('hibernate_sequence'), 'Bruno', 'Cathala', 'bruno', 'cathala', 0, 14);
INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, fk_contact)
VALUES (nextval('hibernate_sequence'), 'Bruno', 'Faduitti', 'bruno', 'faduitti', 0, 17);
INSERT INTO public.creator (id, first_name, last_name, lower_case_first_name, lower_case_last_name, role, fk_contact)
VALUES (nextval('hibernate_sequence'), 'Mihajlo', 'Dimitrievski', 'mihajlo', 'dimitrievski', 2, 18);

INSERT INTO public.seller (id, lower_case_name, name, fk_contact)
VALUES (nextval('hibernate_sequence'), 'joué club tulle', 'Joué Club Tulle', 15);
INSERT INTO public.seller (id, lower_case_name, name, fk_contact)
VALUES (nextval('hibernate_sequence'), 'boutique philibert', 'Boutique Philibert', 20);

INSERT INTO public.publisher (id, lower_case_name, name, fk_contact)
VALUES (nextval('hibernate_sequence'), 'kosmos', 'Kosmos', 16);
INSERT INTO public.publisher (id, lower_case_name, name, fk_contact)
VALUES (nextval('hibernate_sequence'), 'asmodee', 'Asmodee', 19);


INSERT INTO public.game (id, core_rules, description, edition_number, ending, goal, lower_case_name, max_age,
                         max_number_of_player, min_age, min_month, min_number_of_player, name, nature, play_time,
                         preparation, size, stuff, variant, core_game_id, fk_product_line)
VALUES (nextval('hibernate_sequence'), '354566',
        'Vous voilà à la tête de colons fraîchement débarqués sur l''île de Catane. Votre but va être d''installer vos ouailles et de faire prospérer vos colonies en construisant des villes et en utilisant au mieux les matières premières qui sont à votre disposition.
Les Colons de Catane est un jeu tactique de placement, de développement et de négociation. Le hasard y est présent et peut à tout moment contrarier vos plans.',
        null, '354567', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit',
        'les colons de catane', 0, 4, 8, 0, 2,
        'Les Colons de Catane', 1, '90 minutes', null, 'Petit',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie, dui id blandit', null, null,
        (SELECT id FROM product_line WHERE name = 'Catane'));

INSERT INTO public.game_copy (id, date_of_purchase, general_state, is_loanable, location, object_code, price,
                              register_date, wear_condition, fk_game, fk_publisher, fk_seller)
VALUES (nextval('hibernate_sequence'), '2018-05-20', 0, true,
        'Étagère jeu famille', '00050', 40.00, '2021-07-03', 'Bon état',
        (SELECT id FROM game WHERE name = 'Les Colons de Catane'),
        (SELECT id FROM publisher WHERE name = 'Kosmos'),
        (SELECT id FROM seller WHERE name = 'Joué Club Tulle'));

INSERT INTO public.loan (id, is_closed, loan_end_time, loan_start_time, fk_account, fk_game_copy)
VALUES (nextval('hibernate_sequence'), true, '2020-08-16', '2020-07-16',
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

