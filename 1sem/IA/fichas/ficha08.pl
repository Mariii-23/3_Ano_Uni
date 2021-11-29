%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ITELIGÊNCIA ARTIFICIAL - MiEI/LEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariantes

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% PROLOG: definicoes iniciais

:- op(900,xfy,'::' ).
:- dynamic filho/2.
:- dynamic pai/2.
:- dynamic avo/2.
:- dynamic bisavo/2.
:- dynamic trisavo/2.
:- dynamic neto/2.
:- dynamic descendente/3.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado filho: Filho,Pai -> {V,F,D}
filho( joao,jose ).
filho( jose,manuel ).
filho( carlos,jose ).
%% :- include('ficha01.pl').

% Invariante Estrutural:  nao permitir a insercao de conhecimento
%                         repetido

+filho( F,P ) :: (findall( (F,P),(filho( F,P )),S ),
                  comprimento( S,N ), 
                  N == 1
                  ).

% Invariante Referencial: nao admitir mais do que 2 progenitores
%                         para um mesmo individuo

+filho( F,P ) :: (
                findall( F , filho(F,_) , S ),
                comprimento(S,N),
                N=<2
 ).

%% ii
+pai(P,F):: (findall(P,pai(P,F),S),
             comprimento(S,N),
             N==1
            ).

%% iii
+neto(N,A):: (findall(A,neto(N,A),S),
             comprimento(S,L),
             L==1
            ).

%% IV
+avo(A,N):: (findall(A,avo(A,N),S),
             comprimento(S,L),
             L==1
            ).

%% V
+descendente(D,A,G):: (findall(descendente(D,A,G),descendente(D,A,G),S),
                       comprimento(S,N), N==1
                      ).

%% Vi
+filho(F,P):: (findall(F, filho(F,P),S),
               comprimento(S,N), N=<2
              ).
%% Vii
+neto(N,A):: (findall(A,neto(N,A),S),
              comprimento(S,L),
              L=<4
             ).
%% Ix
+avo(A,N):: (findall(A,avo(N,A),S),
              comprimento(S,L),
              L=<4
             ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento
validar([]).
validar([A|T]):- A , validar(T).

insercao(Termo):- assert(Termo).
insercao(Termo):- retract(Termo), !, fail.

remover(Termo):- assert(Termo), !, fail.
remover(Termo):- retract(Termo).

evolucao( Termo ):-
    findall(Invariante,+Termo::Invariante,Lista),
    insercao(Termo),
    validar(Lista).

comprimento([],0).
comprimento([_|T],R):- comprimento(T,N), R is N+1.
