insert into genre_category(genre_category_id, name, registration_date) values 
(1, 'Common Genres', now()),
(2, 'Explicit Genres', now()),
(3, 'Themes', now()),
(4, 'Demographics', now());

alter sequence genre_category_genre_category_id_seq restart with 5;
