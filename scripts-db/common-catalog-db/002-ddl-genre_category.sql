create table genre_category(
    genre_category_id bigserial primary key,
    name varchar(200) not null,
    registration_date timestamptz not null
);