create table anime_studio(
    anime_studio_id bigserial primary key,
    name varchar(200) not null,
    registration_date timestamptz not null
);