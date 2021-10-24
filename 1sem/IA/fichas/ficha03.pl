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

% és tone, isto ta mal e tem erros
accListaDiff([X],L,N):- N is 1.
accListaDiff([],L,N):- N is len(L,N).
accListaDiff([A|T],L,N):- member(A,T), accListaDiff(T,L,N).
accListaDiff([A|T],L,N):- accListaDiff(T,[A|L],X), N is X+1.
lDiff(L,N):- accListaDiff(L,[],0).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagar: Elemento,Lista,Resultado -> {V,F}

% - tem erro
accDel(_,[],L).
% -  aqui devia returnar o L ++ T
accDel(X,[X|T],L):- accDel(X,T,L).
accDel(X,[X1|T],L):- accDel(X,T,N), L is [X1|N].
del(X,L):- accDel(X,L,[]).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagatudo: Elemento,Lista,Resultado -> {V,F}





%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado adicionar: Elemento,Lista,Resultado -> {V,F}





%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado concatenar: Lista1,Lista2,Resultado -> {V,F}





%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado inverter: Lista,Resultado -> {V,F}

accReverse([],L,L).
accReverse([H|T],Acc,Rev):- accReverse(T,[H|Acc],Rev).
reverse(L1,L2):- accReverse(L1,[],L2).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado sublista: SubLista,Lista -> {V,F}





%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).
