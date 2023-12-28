create table account(
    id serial primary key,
    name varchar(30) unique
);

create table pet(
    id serial primary key,
    name varchar(30),
    age integer,
    height real,
    color varchar(16),
    sex varchar(10),
    account_id integer not null,

    foreign key(account_id) references account(id)
)