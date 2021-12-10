% use_module(library(listing)). -> quando o listing deixa de dar
%

%TODO acabar as alineas de inserir e remover

:- dynamic '-'/1.
:- dynamic jogo/3.
:- op(900,xfy,'::').

+jogo(J,A,_) :: (
         findall(Custos, (jogo(J,A,Custos),not(nulo(Custos))) ,R),
         length(R,0)
                  ).

-jogo(J,A,C):-
    not(jogo(J,A,C)),
    not(exception(jogo(J,A,C))).

-jogo(7,'Guerra Godinho', 2500).

exception(jogo(J,A,_)):- jogo(J,A,xpto).
exception(jogo(J,A,_)):- jogo(J,A,sumiu). % sera interdito
exception(jogo(3,'Costa Carvalho',500)).
exception(jogo(3,'Costa Carvalho',2000)).
exception(jogo(4,'Duarte Durão',Custo)):- Custo > 249 , 751 > Custo.
exception(jogo(6,'Francisco França', Custo)):- Custo > 4999 .
exception(jogo(8,'Hélder Heitor',Custo)):- Custo > 900 , 1100 > Custo. %% sem certezas neste
exception(jogo(9,'Ivo Inocêncio',Custo)):- Custo > 2999 , 3001 > Custo. %% sem certezas neste

nulo(sumiu).

jogo(1,'Almeida Antunes',500).
jogo(2,'Baltazar Borges',xpto).
jogo(5,'Edgar Esteves', sumiu).
jogo(6,'Francisco França', 250).
jogo(7,'Guerra Godinho', xpto).

%%%%%%%%% funcoes auxiliares %%%%%%%%%%%%%%%%%%
si(Questao,verdadeiro):- Questao.
si(Questao,falso):- -Questao.
si(Questao,desconhecido):- not(Questao), not(-Questao).


%% Verificar se é valido
valid([]).
valid([A|T]):- A, valid(T).

%% Inserir um novo conhecimento
inserir(New):- assert(New).
inserir(New):- retract(New), !, fail.

%% Remover conhecimento
remover(X):- retract(X).
remover(X):- assert(X), !, fail.

%% Inserir um novo conhecimento verificando se este se encontra valido
new_predicado(P):-
    findall(X,+P::X,R),
    inserir(P),
    valid(R).

%% Remover um dado conhecimento, garantindo que este pode ser removido
remover_predicado(P):-
    findall(X,-P::X,R),
    remover(P),
    valid(R).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

newJogo(J,A,C):- new_predicado(jogo(J,A,C)).
