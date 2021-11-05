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

%Todo verificar
% acho q ta mal
ciclo(G,A,P):- aux_ciclo(G,A,[],P).

aux_ciclo(_,A,[A|P],[A|P]).
aux_ciclo(G,A,[Y|P1],P):-
  adjacente(X,Y,_,_,G), caminho1(G,A,[X,Y|P1],P).

%---------------------------------
%alinea 4)



%---------------------------------
%alinea 5)


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

membro(X, [X|_]).
membro(X, [_|Xs]):-
	membro(X, Xs).
