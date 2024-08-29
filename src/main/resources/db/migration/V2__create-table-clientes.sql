create table clientes(

    id bigserial not null,
    nome_completo varchar(255) not null,
    documento varchar(255) not null,
    data_de_nascimento date not null,
    email varchar(255),
    telefone varchar(255),
    ativo boolean,

    primary key(id)

);