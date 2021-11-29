% freguesia: #Nome,Custo,Tempo,[Ruas] -> {V,F}
freguesia('Taipas',23,23,
          [
              rua('Rua Nova'),
              rua('Rua Santo Antonio')
          ]
         ).
freguesia('Briteiros',30,100,
          [
              rua('Rua Velha')
          ]
         ).
freguesia('Sao Vitor',50,120,
          [
              rua('Rua Bernardo')
          ]
         ).

% transporte: #Id,Nome,Velocidade,Carga,Nivel_Ecologico -> {V,F}
transporte(1,'bicicleta',  10,5,5).
transporte(2,'mota',       35,20,3).
transporte(3,'carro',      25,100,2).
transporte(4,'nada',      25,100,2).

% estafeta: #ID,Nome -> {V,F}
estafeta(1,'Bernardo').
estafeta(2,'Sofia').
estafeta(3,'Costa').
estafeta(4,'Filipe').

% cliente: #Id,Nome,Morada -> {V,F}
cliente(1,'Leonardo',      morada('Rua Nova','Taipas')).
cliente(2,'Leonor'  ,      morada('Rua Velha','Briteiros')).
cliente(3,'Alexandre',     morada('Rua Bernardo','Sao Vitor')).
cliente(4,'Maria Dinis',   morada('Rua Santo Antonio','Taipas')).
cliente(5,'Mafalda Bravo', morada('Rua Velha','Briteiros')).
cliente(6,'Tomas Faria',   morada('Rua Bernardo','Sao Vitor')).

% encomenda: #Id,Id/NomeCliente,Peso,Volume,Dia Pedido: #D/M/Y/H ,Limite: #D/H
encomenda(1, 1/'Leonardo'       ,2,  2,  23/11/2021/10, 0/2).
encomenda(2, 1/'Leonardo'       ,5,  10, 24/11/2021/23, 4/0).
encomenda(3, 3/'Alexandre'      ,2,  2,  23/11/2021/16, 1/0).
encomenda(4, 5/'Maria Dinis'    ,5,  2,  10/11/2021/9, 0/2).
encomenda(5, 6/'Tomas Faria'    ,10, 10, 12/10/2021/11, 4/0).
encomenda(6, 2/'Leonor'         ,5,  2,  13/09/2021/17, 1/0).

% servico: #Id,Id_estafeta,Id_encomenda,Id Transporte,DiaEntrega:# D/M/Y/H,Classificacao
servico(1, 3, 4, 3, 10/11/2021/10, 5).
servico(1, 4, 5, 2, 14/10/2021/12, 2).
servico(1, 1, 6, 1, 14/09/2021/14, 4).
