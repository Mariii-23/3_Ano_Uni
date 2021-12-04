%% use_module(library(listing)). -> quando o listing deixa de dar
:- dynamic '-'/1.
:- dynamic jogo/3.

%% :- op(900,xfy,'::').

%% +jogo(J,A,_) :: (
%%         findall(Custos, (jogo(J,A,Custos),not(nulo(Custos))) ,R),
%%         length(R,0)
%%                  ).

-jogo(J,A,C):-
    not(jogo(J,A,C)),
    not(exception(jogo(J,A,C))).

exception(jogo(J,A,_)):- jogo(J,A,xpto).
exception(jogo(J,A,_)):- jogo(J,A,sumiu). % sera interdito
exception(-jogo(3,'Costa Carvalho',500)).
exception(-jogo(3,'Costa Carvalho',2000)).
exception(jogo(4,'Duarte Durão',Custo)):- Custo > 249 , 749 > Custo.

nulo(sumiu).

jogo(1,'Almeida Antunes',500).
jogo(2,'Baltazar Borges',xpto).

%jogo(3,'Costa Carvalho',500).
%jogo(3,'Costa Carvalho',2000).
jogo(5,'Edgar Esteves', sumiu).

%%%%%%%%% funcoes auxiliares %%%%%%%%%%%%%%%%%%

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
    %% not(P),
    findall(X,+P::X,R),
    inserir(P),
    valid(R).

%% Remover um dado conhecimento, garantindo que este pode ser removido
remover_predicado(P):-
    %% P,
    findall(X,-P::X,R),
    remover(P),
    valid(R).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

newJogo(J,A,C):- new_predicado(jogo(J,A,C)).
