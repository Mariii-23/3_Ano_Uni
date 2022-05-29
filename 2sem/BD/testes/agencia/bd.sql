
create schema if not exists `agencia` DEFAULT CHARACTER SET utf8 ;
USE `agencia`;

Create table if not exists `area_investigacao` (
    num int not null unique AUTO_INCREMENT,
    nome VARCHAR(30) not null,
    Primary key (num)
       );

Create table if not exists `investigador` (
    num_profissional int not null unique AUTO_INCREMENT,
    area int Not null,
    nome VARCHAR(1024) unique not null,
    data_nascimento DATE not null,

    Primary key (num_profissional),
    Foreign key (area) references `area_investigacao`(num)
       );

Create table if not exists `telemovel` (
    telemovel int(9) not null unique,
    investigador int not null,
	
    Primary key (telemovel),
    Foreign key (investigador) references `investigador` (num_profissional)
       );

Create table if not exists `email` (
    email varchar(1024) not null unique,
    investigador int not null,

    Primary key (email),
    Foreign key (investigador) references `investigador` (num_profissional)
);
