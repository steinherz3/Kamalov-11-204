drop table if exists user_tournament;
drop table if exists tournament;
drop table if exists stock;
drop table if exists booking;
drop table if exists pc;
drop table if exists users;

create table users(
                      id bigserial primary key,
                      username varchar,
                      email varchar,
                      hash_password varchar,
                      user_role varchar default 'user'
);

create table pc(
                   id bigserial primary key,
                   start_working_time time,
                   end_working_time time
);

create table booking(
                        id bigserial primary key,
                        user_id bigint,
                        foreign key (user_id) references users(id),
                        pc_id bigint,
                        foreign key (pc_id) references pc(id),
                        from_time timestamp,
                        to_time timestamp,
                        status varchar default 'waiting'
);

create table stock(
                      id bigserial primary key,
                      name varchar,
                      count bigint,
                      price bigint
);

create table tournament(
                           id bigserial primary key,
                           name varchar,
                           description text,
                           start_time timestamp
);

create table user_tournament(
                                id bigserial primary key,
                                user_id bigint,
                                foreign key (user_id) references users(id),
                                tournament_id bigint,
                                foreign key (tournament_id) references tournament(id),
                                unique (user_id, tournament_id)
);