:- dynamic '-'/1.
:- op(900,xfy,'::').
:- dynamic obra/4.

-obra(T,A,E,L):-
    not(obra(T,A,E,L)),
    not(exception(obra(T,A,E,L))).

exception(obra(T,A,_,_)):- obra(T,A,incerto,incerto).
exception(obra(T,A,E,_/M)):- obra(T,A,E,incerto/M).
exception(obra(T,A,E,_)):- obra(T,A,E,incerto).
exception(obra(T,_,E,D)):- obra(T,incerto,E,D).
exception(obra('Marquei um golo','Pereira Peres',E,D/M)):-
    ( E == 'Sahida' ; E == 'Difícil' ),
    ( (M == abril , (D < 31; 0 < D)) ;
      (M == maio , D == 1)).
exception(obra('Bebi um golo',_, 'Amal-a', D/dezembro)):-
    D < 32 , 24 < D.

exception(obra(T, 'Francisco Fongorn',incerto,incerto)):-
     T == 'A Peça'; T == 'O Pessa'.

nulo(interdito).

obra('Eu jogo um jogo', 'António Lemos', 'Commércio', 21/junho).
obra('O espetador de pregos', A,incerto,incerto):-
    A == 'Carlos Vigário' ; A == 'Eva Longornia'.
%% obra(T, 'Eva Longornia',incerto,incerto):-
%%     T == 'A Peça'; T == 'O Pessa'.
obra('O espetador de Tv','Gregório Vomm', 'Exhibição',incerta/maio).
obra('Foi na sede', 'Sousa Neves','Pharmácia',incerto).
obra('Tenho sede',interdito,'Theatro',19/março).

inserir(P):- assert(P).
inserir(P):- retract(P), !, fail.

valid([]).
valid([A|T]):- A , valid(T).

new_predicado(P):-
    findall( X, +P::X, R ),
    inserir(P),
    valid(R).

si(P,verdadeiro):- P.
si(P,falso):- -P.
si(P,desconhecido):- not(P), not(-P).
