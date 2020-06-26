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
