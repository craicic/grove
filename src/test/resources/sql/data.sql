INSERT INTO public.mechanism (id, lower_case_title, title)
VALUES (1, 'négociation', 'Négociation'),
       (2, 'associations d''idées', 'Associations d''idées'),
       (3, 'puzzle', 'Puzzle'),
       (4, 'lettres', 'Lettres'),
       (5, 'programmation', 'Programmation'),
       (6, 'hazard', 'Hazard'),
       (7, 'cartes', 'Cartes'),
       (8, 'dominos', 'Dominos');
SELECT setval('category_sequence', 8, true);


INSERT INTO public.category(id, lower_case_title, title)
VALUES (1, 'stratégie', 'Stratégie'),
       (2, 'urbanisme', 'Urbanisme'),
       (3, 'course', 'Course'),
       (4, 'gestion', 'Gestion'),
       (5, 'commerce', 'Commerce'),
       (6, 'fantastique', 'Fantastique'),
       (7, 'horreur', 'Horreur'),
       (8, 'mythologie', 'Mythologie'),
       (9, 'animaux', 'Animaux'),
       (10, 'histoire', 'Histoire'),
       (11, 'enquête', 'Enquête'),
       (12, 'far west', 'Far West');
SELECT setval('mechanism_sequence', 12, true);

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

INSERT INTO public.game (id, description, lower_case_title, material, max_age, max_number_of_player, min_age, min_month,
                         min_number_of_player, nature, play_time, rules, title, variant, year_of_release)
VALUES (1, 'Chaque joueur dirige un monde voué à s''étendre afin de créer l''empire le plus prospère. ',
        'race for the galaxy', null,
        0,
        4, 14, 0, 2, 2, '45 minutes',
        null, 'Race for the Galaxy', null, '2012-01-01'),
       (2, 'Vous voilà à la tête de colons fraîchement débarqués sur l''île de Catane. Votre but va être d''installer vos ouailles et de faire prospérer vos colonies en construisant des villes et en utilisant au mieux les matières premières qui sont à votre disposition.
Les Colons de Catane est un jeu tactique de placement, de développement et de négociation. Le hasard y est présent et peut à tout moment contrarier vos plans.',
        'les colons de catane',
        null,
        0, 4, 8, 0, 2, 2, '90 minutes'
           , null, 'Les Colons de Catane', null, '2012-01-01');
SELECT setval('game_sequence', 2, true);

INSERT INTO public.game_copy
VALUES (1, '2018-05-20', '2018-05-20', '02SE', 1
           , true, 'Étagère jeu famille', '00050', 40.00,
        1,
        (SELECT id FROM game WHERE title = 'Les Colons de Catane'),
        (SELECT id FROM publisher WHERE name = 'Kosmos'));
SELECT setval('game_copy_sequence', 1, true);

INSERT INTO public.loan (id, is_closed, loan_end_time, loan_start_time, fk_account, fk_game_copy)
VALUES (1, true, '2020-08-16', '2020-07-16',
        (SELECT id FROM account WHERE first_name = 'John' AND last_name = 'Doe'),
        (SELECT id FROM game_copy WHERE object_code = '00050'));
SELECT setval('loan_sequence', 1, true);

INSERT INTO public.game_category (fk_game, fk_category)
VALUES ((SELECT id FROM game WHERE title = 'Les Colons de Catane'), (SELECT id FROM category WHERE title = 'Commerce')),
       ((SELECT id FROM game WHERE title = 'Les Colons de Catane'),
        (SELECT id FROM category WHERE title = 'Stratégie')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'), (SELECT id FROM category WHERE title = 'Stratégie')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'), (SELECT id FROM category WHERE title = 'Gestion'));


INSERT INTO public.game_creator (fk_game, fk_creator)
VALUES ((SELECT id FROM game WHERE title = 'Les Colons de Catane'),
        (SELECT id FROM creator WHERE first_name = 'Bruno' AND last_name = 'Cathala')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Tom' AND last_name = 'Lehmann')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Wei-Hwa' AND last_name = 'Huang')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Claus' AND last_name = 'Stephan')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'),
        (SELECT id FROM creator WHERE first_name = 'Martin' AND last_name = 'Hoffmann'));

INSERT INTO public.game_mechanism (fk_game, fk_mechanism)
VALUES ((SELECT id FROM game WHERE title = 'Les Colons de Catane'),
        (SELECT id FROM mechanism WHERE title = 'Programmation')),
       ((SELECT id FROM game WHERE title = 'Les Colons de Catane'), (SELECT id FROM mechanism WHERE title = 'Hazard')),
       ((SELECT id FROM game WHERE title = 'Les Colons de Catane'),
        (SELECT id FROM mechanism WHERE title = 'Négociation')),
       ((SELECT id FROM game WHERE title = 'Race for the Galaxy'), (SELECT id FROM mechanism WHERE title = 'Cartes'));

-- THIS PART IS SPECIFIC TO TESTS
INSERT INTO image (id, fk_game)
VALUES (1, (SELECT id FROM game WHERE title = 'Les Colons de Catane'));
INSERT INTO image_blob (image_id, content)
VALUES (1, pg_read_binary_file('/data/catane.jpg'));
SELECT setval('image_sequence', 1, true);