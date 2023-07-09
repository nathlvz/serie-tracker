create table country(
    country_id bigint generated by default as identity(start with 253) primary key,
    name varchar(200) not null,
    registration_date timestamp with time zone not null
);