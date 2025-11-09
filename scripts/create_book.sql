create table simple_main.book(
    id uuid primary key DEFAULT gen_random_uuid(),
    name varchar(30),
    release_date timestamp
);