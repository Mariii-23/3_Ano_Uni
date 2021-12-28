
:- dynamic -/1.

%% -voo(X):- nao(voo(X)),
%%           nao(excecao(-voo(X))).

%% -voo(X):- mamifero(X),
%%           nao(excecao(-voo(X))).

%% voo(X):- ave(X).

%% excecao(-voo(X)):- morcego(X).
%% excecao(+voo(X)):- pinguim(X).

%1
voa(X) :- ave(X), nao(excecao(voa(X))).

%2
-voa(X)  :- mamifero(X),nao( excecao(-voa(X))).

%aux predicates
-voa(X) :- excecao(voa(X)).
voa(X) :- excecao(-voa(X)).

%3
-voa( tweety ).

%aux predicates - definir as exceções
excecao(voa(X)) :- avestruz(X).
excecao(voa(X)) :- pinguim(X).
excecao(-voa(X)) :- morcego(X).

%4
ave(pitigui).

%5
ave(X) :- canario(X).

%6
ave(X) :- piriquito(X).

%7
canario(piupiu).

%8
mamifero(silvestre).

%9
mamifero( X ) :- cao( X ).

%10
mamifero( X ) :- gato( X ).

%11
cao(boby).

%12
ave(X) :- avestruz(X).

%13
ave(X) :- pinguim(X).

%14
avestruz(trux).

%15
pinguim(pingu).

%16
mamifero(X) :- morcego(X).

%17
morcego(batemene).
