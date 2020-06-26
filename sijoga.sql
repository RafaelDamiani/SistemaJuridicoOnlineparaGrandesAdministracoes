create table tb_user_type (
	id serial not null primary key,
	user_type_name varchar(8) not null
);
CREATE SEQUENCE seq_user_type;

create table tb_user(
	id bigserial not null primary key,
	user_email varchar(255) not null,
	user_password varchar(255) not null,
	user_name varchar(255) not null,
	user_cpf varchar(14) not null,
	user_type_id integer not null REFERENCES tb_user_type(id)
);
CREATE SEQUENCE seq_user_id;

create table tb_pessoa (
	id integer NOT NULL PRIMARY KEY,
	pess_nome character varying(30) NOT NULL
);

create table tb_endereco (
	id integer NOT NULL PRIMARY KEY,
	end_rua character varying(30) NOT NULL,
	end_numero integer NOT NULL
);
alter table tb_pessoa add end_id integer not null REFERENCES tb_endereco(id);

CREATE SEQUENCE seq_pess_id
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 11
CACHE 1;

CREATE SEQUENCE seq_end_id
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 11
CACHE 1;	q