create table users(

    id bigserial not null,
    username varchar(100) not null unique,
    password varchar(255) not null,

    primary key(id)

);

insert into users values (

    default,
    'admin',
    '$2a$12$oByYgobc7cquoRLUAJPNTeYEwbyhCOXwTaUGS35.wzY8H.3qYnXki'

);

