%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - 3/MiEI

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica
% Grafos (Ficha 9)

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Diferentes representacaoes de grafos
%
%lista de adjacencias: [n(b,[c,g,h]), n(c,[b,d,f,h]), n(d,[c,f]), ...]
%
%termo-grafo: grafo([b,c,d,f,g,h,k],[e(b,c),e(b,g),e(b,h), ...]) or
%            digrafo([r,s,t,u],[a(r,s),a(r,t),a(s,t), ...])
%
%clausula-aresta: aresta(a,b)


%---------------------------------

g1( grafo([madrid, cordoba, braga, guimaraes, vilareal, viseu, lamego, coimbra, guarda],
  [aresta(madrid, corboda, a4, 400),
   aresta(braga, guimaraes,a11, 25),
   aresta(braga, vilareal, a11, 107),
   aresta(guimaraes, viseu, a24, 174),
   aresta(vilareal, lamego, a24, 37),
   aresta(viseu, lamego,a24, 61),
   aresta(viseu, coimbra, a25, 119),
   aresta(viseu,guarda, a25, 75)]
 )).

%---------------------------------
%alinea 1)

adjacente(X,Y,K,E, grafo(_,Es)) :- member(aresta(X,Y,K,E),Es).
adjacente(X,Y,K,E, grafo(_,Es)) :- member(aresta(Y,X,K,E),Es).

%---------------------------------
%alinea 2)

% caminho acíclico P, que comeca no nó A para o nó B no grafo G

caminho(G,A,B,P):- caminho1(G,A,[B],P).
caminho1(_,A,[A|P1],[A|P1]).
caminho1(G,A,[Y|P1],P):-
   adjacente(X,Y,_,_,G), nao(membro(X,[Y|P1])), caminho1(G,A,[X,Y|P1],P).

%---------------------------------
% alinea 3)

% A B A -> é possivel
% A B C B A -> não é possivel

ciclo(G,A,[A,X|Y]) :- adjacente(A,X,_,_,G), caminho(G,X,A,P), P = [X|Y].

% Outra versao

%ciclo(G,A,P):- aux_ciclo(G,A,[A],P).

%aux_ciclo(_,A,[A,P|T],[A,P|T]).
%aux_ciclo(G,A,[Y|P1],[A,Y|P1]):-
%  adjacente(A,Y,_,_,G).
%aux_ciclo(G,A,[Y|P1],P):-
%  adjacente(X,Y,_,_,G), nao(membro(X,[Y|P1])), aux_ciclo(G,A,[X,Y|P1],P).

%---------------------------------
%alinea 4)

caminhoK(G,A,B,P,Km,Es) :- caminhoK_aux(G,A,[B],P,0,Km,[],Es).

caminhoK_aux(_,A,[A|P1],[A|P1],Km,Km,Es,Es).

caminhoK_aux(G,A,[Y|P1],P ,Km_agora, Km, Es_T ,Es ):-
    adjacente(X,Y,Estrada,Km1,G), nao(membro(X,[Y|P1])),
    Km2 is Km_agora + Km1,
    caminhoK_aux(G,A,[X,Y|P1],P,Km2, Km , [ Estrada|Es_T] , Es)
.


%---------------------------------
%alinea 5)

cicloK(G,A,[A,X|Y], Km , [Estrada_i|Es2] ) :-
    adjacente(A,X,Estrada_i,Km_i,G),
    caminhoK(G,X,A,P, Km2, Es2 ) , P = [X|Y],
    Km is Km_i + Km2
.

% Outra versao

%cicloK(G,A,P,Km,Es):- aux_cicloK(G,A,[A],P,0,Km,[],Es).

%aux_cicloK(_,A,[A,P|T],[A,P|T],Km,Km,Es,Es).
%aux_cicloK(G,A,[Y|P1],[A,Y|P1],Km1, Km, Es , [Estrada|Es]):-
%    adjacente(A,Y,Estrada,Km2,G),
%    Km is Km1 + Km2.
%aux_cicloK(G,A,[Y|P1],P,Km_atual,Km, Es_T ,Es):-
%    adjacente(X,Y,Estrada,Km1,G),
%    nao(membro(X,[Y|P1])),
%    Km2 is Km1 + Km_atual,
%    aux_cicloK(G,A,[X,Y|P1],P, Km2, Km, [Estrada|Es_T] ,Es).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( _ ).

membro(X, [X|_]).
membro(X, [_|Xs]):-
	membro(X, Xs).
