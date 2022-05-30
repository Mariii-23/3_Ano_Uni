
create schema if not exists `federacao` DEFAULT CHARACTER SET utf8 ;
USE `federacao`;

create table if not exists `jogador` (
	id int not null AUTO_INCREMENT,
    nome VARCHAR(100) not null,
    nr_camisola int not null,
    posicao int,
    idade int not null,
    
    primary key (id)
);

create table if not exists `equipa_info` (
	id int not null AUTO_INCREMENT,
    nome VARCHAR(100) not null,
    treinador VARCHAR(100) not null unique,
    
    primary key (id, nome)
    );
    
create table if not exists `equipa` (
	equipa int not null,
    jogador int not null unique,
    
    primary key (equipa, jogador),
	foreign key (equipa) references equipa_info (id),
	foreign key (jogador) references jogador (id)
);

-- povoamento da base de dados

insert jogador (nome, nr_camisola, posicao, idade)
Values ('Maria' , 7, 1, 23), ('Joao', 12, 2, 30),
		('Alberto' , 7, 1, 23), ('Leonor', 12, 2, 30);
        
insert equipa_info (nome, treinador) Values 
('porto', 'Arroz'), ('benfica', 'batata');

insert equipa (equipa, jogador) values 
(1,1), (1,2), (2,3), (2,4);

-- insert equipa (equipa, jogador) values 
-- (1,1), (2,3) ;

select * from equipa;

-- sem o treinador funciona e nao sei porque
select e.nome, e.treinador , count(equipa.jogador) as `num_jogadores`  from equipa 
	inner join equipa_info as e
    on e.id = equipa.equipa
    group by e.nome
;