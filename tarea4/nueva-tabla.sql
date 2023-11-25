CREATE TABLE comentario (
    id                   int8           auto_increment,
    nombre               varchar(255)   not null,
    email                varchar(255)   not null,
    fecha                datetime       not null,
    comentario           varchar(512)   not null,
    id_hincha            INT            null,
    id_artesano          INT            null,
    primary key(id),
    foreign key(id_hincha) references hincha(id),
    foreign key(id_artesano) references artesano(id)
) 
ENGINE = INNODB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;
