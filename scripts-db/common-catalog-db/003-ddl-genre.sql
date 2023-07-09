create table genre(
    genre_id bigserial primary key,
    genre_category_id bigint, foreign key(genre_category_id) references genre_category(genre_category_id),
    name varchar(200) not null,
    registration_date timestamptz not null
);