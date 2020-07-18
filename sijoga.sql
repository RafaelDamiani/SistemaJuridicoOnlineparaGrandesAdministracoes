create table tb_user_type (
	id serial not null primary key,
	user_type_name varchar(8) not null unique
);

insert into tb_user_type (user_type_name) values 
('Admin'),
('Advogado'),
('Juiz'),
('Parte');

create table tb_user(
	id bigserial not null primary key,
	user_email varchar(255) not null unique,
	user_password varchar(255) not null,
	user_name varchar(255) not null,
	user_cpf varchar(14) not null unique,
	user_type_id integer not null REFERENCES tb_user_type(id)
);

create table tb_address(
	id bigserial not null primary key,
	address_zip_code varchar(8) not null,
	address_street varchar(255) not null,
	address_number smallint not null,
	address_city varchar(255) not null,
	address_state varchar(2) not null,
	user_id bigint not null REFERENCES tb_user(id)
);	

create table tb_prosecution(
	id bigserial not null primary key,
	prosecution_date timestamp not null,
	judge_id bigint not null REFERENCES tb_user(id)
);

create table tb_prosecution_status(
	id serial not null primary key,
	prosecution_status_name varchar(9) not null unique
);

insert into tb_prosecution_status (prosecution_status_name) values
('Aberto'),
('Encerrado'),
('Ganho'),
('Perdido');

create table tb_part_type(
	id serial not null primary key,
	part_type_name varchar(10) not null unique
);

insert into tb_part_type(part_type_name) values 
('Promovente'),
('Promovido');

create table tb_prosecution_user(
	id bigserial not null primary key,
	prosecution_id bigint not null REFERENCES tb_prosecution(id),
	part_id bigint not null REFERENCES tb_user(id),
	lawyer_id bigint not null REFERENCES tb_user(id),
	prosecution_status_id integer not null REFERENCES tb_prosecution_status(id),
	part_type_id integer not null REFERENCES tb_part_type(id)
);

create table tb_phase_type(
	id serial not null primary key,
	phase_type_name varchar(12) not null unique
);

insert into tb_phase_type(phase_type_name) values
('Informativa'),
('Deliberativa');

create table tb_phase_status(
	id serial not null primary key,
	phase_status_name varchar(13) not null unique
);

insert into tb_phase_status(phase_status_name) values
('Pedido Aceito'),
('Pedido Negado'),
('Intimação');

create table tb_phase(
	id bigserial not null primary key,
	phase_date timestamp not null,
	phase_title varchar(255) not null,
	phase_description varchar(5000) not null,
	phase_justification varchar(5000),
	prosecution_id bigint not null REFERENCES tb_prosecution(id),
	lawyer_id bigint not null REFERENCES tb_user(id),
	phase_type_id integer not null REFERENCES tb_phase_type(id),
	phase_status_id integer REFERENCES tb_phase_status(id)
);