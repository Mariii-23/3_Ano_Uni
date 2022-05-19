use empresa;
show databases;
show tables;

-- aqui conta com os valores nulos, logo se tiver um absorve
select count(*) from funcionario;

-- aqui ja nao contem valores nulos
select count(endereco) from funcionario;

-- conta todos ate os repetidos
select count(data_nascimento) from funcionario;

-- sem elementos repetidos
select count(distinct data_nascimento) from funcionario;


select count(distinct cpostal), max(data_nascimento) from funcionario;

-- agrupar resultados

select cpostal, count(*) from funcionario
group by cpostal
order by 2 desc;


select cpostal, count(*) from funcionario
group by cpostal
having count(*) > 3;

-- 10 Indique quantos funcionários estão associados a cada uma das categorias. Apresenta o resultado na seguinte forma; <descricao>| <numero de funcionários> e ordenado por ordem descrecente do numero de funcionários.
select descricao, count(*) as "Número de Funcionários" from categoria 
inner join  funcionario on id = id_categoria
group by descricao order by 2 desc;

-- 11 Altere a forma como resolveu a questão anterior de modo a aparecerem também as categorias sem funcionários ativos.
select descricao, count(id_categoria) as "Número de Funcionários" from categoria
left join  funcionario on id = id_categoria 
group by descricao order by 2 desc;

-- 12 Qual o salario maximo e minimo pago para cada uma das categorias.
select descricao, count(id_categoria) as "Número de Funcionários" from categoria
left join  funcionario on id = id_categoria 
-- left join contrato on tipo_contrato.id = 
group by descricao order by 2 desc;