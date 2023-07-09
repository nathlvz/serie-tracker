insert into anime_studio(anime_studio_id, name, registration_date) values
(1, '8bit', now()),
(2, '81 Produce', now()),
(3, '10Gauge', now()),
(4, '8PAN', now()),
(5, '7doc', now()),
(6, '5 Inc.', now()),
(7, '1IN', now()),
(8, 'Youth Film Studio', now()),
(9, '33 Collective', now());

alter sequence anime_studio_anime_studio_id_seq restart with 74;