create table tarifas(

    id bigserial not null,
    nome_tarifa varchar(255) not null,
    valor_base numeric(7,2) not null,
    ativo boolean,

    primary key(id)

);

create table tarifa_detalhamentos(

    id bigserial not null,
    tarifa_id bigint not null,
    classificacao_hospede varchar(255) not null,
    valor_hospede_adicional numeric(7,2) not null,

    primary key(id)

);