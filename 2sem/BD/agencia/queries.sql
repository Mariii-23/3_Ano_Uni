USE `agencia`;

insert into `area_investigacao` (nome)
  Values ('crime'), ('fraude'), ('rapto');

insert into `investigador` (area, nome, data_nascimento)
  Values (1,'Alberto Rodrigues', '2002-02-12'),
         (2,'Leonor Pereira', '1999-12-12');

insert into `telemovel` (telemovel, investigador)
  Values (253123345, 1), (921123123, 1),
         (245678678, 2), (912123123, 2);

Select i.nome, i.data_nascimento, area.nome as `area` from `investigador` as i
       inner join `area_investigacao` as area
       on i.area = area.num
		
    order by nome asc;

select area.nome, count(i.num_profissional) from `investigador` as i
       inner join `area_investigacao` as area
	   where i.area = area.num 
       group by area.nome
       ;