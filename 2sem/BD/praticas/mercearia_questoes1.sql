use empresa;

-- 1. Apresentar o nome funcionário e respetiva categoria (descricao).
 
  select nome as "Nome do Funcionário", descricao from funcionario
    inner join  categoria on id = id_categoria;
 
 
 -- 2 Apresentar o nome funcionário e respetiva categoria (id + descricao).
    
      select nome as "Nome do Funcionário", concat(id,' - ',descricao) "Categoria" from funcionario
    inner join  categoria on id = id_categoria;
    
-- 3 Apresentar o nome funcionário e respetiva categoria (id + descricao) nascidos depois do ano 1990.

select year(data_nascimento) "Ano Nascimento" from funcionario;

     select nome as "Nome do Funcionário", concat(id,' - ',descricao) "Categoria",year(data_nascimento) "Ano Nascimento"
     from funcionario
    inner join  categoria on id = id_categoria
    where year(data_nascimento) >1990;
    
-- 4 Apresentar o nome funcionário cuja descriçao da categoria é "Segurança".

    select nome as "Nome do Funcionário" from funcionario
    inner join  categoria on id = id_categoria
    where descricao='Segurança';
    
    select  nome as "Nome do Funcionário" from funcionario
    where id_categoria in (select id from categoria where descricao='Segurança');

 
-- 5 Qual a descrição da categoria dos funcionários nascidos até 1990.
    
     select distinct descricao "Categoria" from funcionario
    inner join  categoria on id = id_categoria
    where year(data_nascimento)<=1990;
    
    select descricao "Categoria" from categoria
    where id in (
    select id_categoria from funcionario where year(data_nascimento)<=1990);
    
-- 6 Qual a descrição da categoria dos funcionários nascidos entre 1980 e 1990.
    
     select descricao "Categoria" from categoria
    where id in (
    select id_categoria from funcionario where year(data_nascimento)>=1980 and year(data_nascimento)<=1990);
    
    select descricao "Categoria" from categoria
    where id in (
    select id_categoria from funcionario where year(data_nascimento) between 1980 and 1990);
    
        select descricao "Categoria" from categoria
    where id in (
    select id_categoria from funcionario where year(data_nascimento) in (1980,1981,1982,1982,1984,1985,1986,1987,1988,1989,1990));
    
-- 7 Indique o nome e categoria dos funcionarios que são responsáveis de ramo alimentar no Centro Comercial do Norte.
    
    select funcionario.nome as "Nome do Funcionário", concat(categoria.id,' - ',descricao) "Categoria" from funcionario
    inner join  categoria on categoria.id = id_categoria
    inner join diretor_ramo on id_func = numero
    inner join centro_comercial on centro_comercial.id = id_cc
    inner join ramo_atividade on ramo_atividade.id_ramo=diretor_ramo.id_ramo
    where ramo='Ramo Alimentar' and centro_comercial.nome='Centro Comercial do Norte';
    
    select nome as "Nome do Funcionário", concat(categoria.id,' - ',descricao) "Categoria" from funcionario
    inner join  categoria on categoria.id = id_categoria
    inner join diretor_ramo on id_func = numero
    inner join (select id from centro_comercial  where centro_comercial.nome='Centro Comercial do Norte') cc
    on cc.id = id_cc
    inner join (select id_ramo from ramo_atividade where ramo='Ramo Alimentar' ) ra
    on ra.id_ramo=diretor_ramo.id_ramo;
    
    select nome as "Nome do Funcionário", concat(id,' - ',descricao) "Categoria" from funcionario
    inner join  categoria on id = id_categoria
    where numero in (select id_func from diretor_ramo where
            id_ramo in (select id_ramo from ramo_atividade where ramo='Ramo Alimentar' ) and
            id_cc in (select id from centro_comercial  where centro_comercial.nome='Centro Comercial do Norte'));
    
-- 8 Indique o nome e categoria dos funcionarios que não são responsáveis de ramo no Centro Comercial do Norte.    
    
    select nome as "Nome do Funcionário", concat(id,' - ',descricao) "Categoria" from funcionario
    inner join  categoria on id = id_categoria
    where numero not in
    (select id_func from diretor_ramo where
            id_cc in (select id from centro_comercial  where nome='Centro Comercial do Norte'));
            
-- 9 Indique o nome e categoria dos funcionarios e o nome do seu supervisor.
    
select f1.nome "Nome do Funcionário" ,concat(id,' - ',descricao) "Categoria" , f2.nome "Supervisor"
    from funcionario f1 inner join categoria on f1.id_categoria=categoria.id
    inner join funcionario f2 on f1.supervisor=f2.numero;
    
    select f1.nome "Nome do Funcionário" ,concat(id,' - ',descricao) "Categoria" , ifnull(f2.nome,'* Sem supervisor') "Supervisor"
    from funcionario f1 inner join categoria on f1.id_categoria=categoria.id
    left outer join funcionario f2 on f1.supervisor=f2.numero;