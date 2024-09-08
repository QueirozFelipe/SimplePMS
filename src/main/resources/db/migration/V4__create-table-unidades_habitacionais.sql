create table unidades_habitacionais(

    id bigserial not null,
    nome_uh varchar(255) not null,
    categoria_de_uh_id bigint not null,
    ativo boolean,

    primary key(id),
    constraint fk_unidades_habitacionais_categoria_id foreign key(categoria_de_uh_id) references categorias_de_uh(id)

);