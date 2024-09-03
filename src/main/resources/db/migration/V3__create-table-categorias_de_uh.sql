create table categorias_de_uh(

    id bigserial not null,
    nome_categoria varchar(255) not null,
    quantidade_de_leitos smallint,
    ativo boolean,

    primary key(id)

);