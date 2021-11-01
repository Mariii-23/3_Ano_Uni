%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% inteligência Artificial - MiEI/3 LEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Operacoes sobre listas.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pertence: Elemento,Lista -> {V,F}

pertence( X,[X|_] ).
pertence( X,[Y|L] ) :-
    X \= Y,
    pertence( X,L ).

member(X,[X|_]).
member(X,[_|T]):- member(X,T).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado comprimento: Lista,Comprimento -> {V,F}

comprimento( [],0 ).
comprimento( [_|L],N ) :-
    comprimento( L,N1 ),
    N is N1+1.

len([],0).
len([_|T],N):- len(T,X), N is X+1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado quantos: Lista,Comprimento -> {V,F}
diff(_,[],0).
diff(A,[A|T],N):- diff(A,T,N).
diff(A,[_|T],N):- diff(A,T,X), N is X+1.

nPred(_,[],0).
nPred([],_,0).
nPred([A|T],[A1|T1],N):-
    A \= A1,
    N is 0.
nPred([A|T],[A|T1],N):-
    nPred(T,T1,X),
    N is X+1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagar: Elemento,Lista,Resultado -> {V,F}

apaga1(_, [], []).
apaga1(X, [X|T], T).
apaga1(X, [H|T], [H|R]) :-
    X \= H,
    apaga1(X, T, R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagatudo: Elemento,Lista,Resultado -> {V,F}

apagaTudo(_, [], []).
apagaTudo(X, [X|T], R) :-
    apagaTudo(X, T, R).
apagaTudo(X, [H|T], [H|R]) :-
    X \= H,
    apagaTudo(X, T, R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado adicionar: Elemento,Lista,Resultado -> {V,F}

add_cauda(X,L,L):- member(X,L).
add_cauda(X,L,[X|L]).

adicionar(X, [], [X]).
adicionar(X,[X1|T], [X1|R] ):-
    X \= X1,
    adicionar(X, T, R).
adicionar(_,L,L).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado concatenar: Lista1,Lista2,Resultado -> {V,F}

concatenar(L1,[],L1).
concatenar(L1,[A|T],R):-
    adicionar(A,L1,R1),
    concatenar(R1,T,R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado inverter: Lista,Resultado -> {V,F}

%- com auxiliar
accReverse([],L,L).
accReverse([H|T],Acc,Rev):- accReverse(T,[H|Acc],Rev).

%- sem auxiliar
reverse(L1,L2):- accReverse(L1,[],L2).
rev([X],[X]).
rev([A|T],[A|L2]):- rev(T,L2).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado sublista: SubLista,Lista -> {V,F}

prefixo([],_).
% prefixo([A|_],[A1|_]):- A\=A1, fail.
prefixo([A|T],[A|T1]):- prefixo(T,T1).

sublista([],_).
sublista(L,L1):-  prefixo(L,L1).
sublista(L,[A|T]):-  sublista(L,T).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).
