:- dynamic '-'/1.
:- op(900,xfy,'::').
:- dynamic aluno/6.


% Invariantes
+aluno(_,_,_,_,_,_):: (
     findall(Id, (aluno(Id,_,_,A,E,_), (A > 5 ; E > 300)), S ),
     length(S,0)
 ).

+aluno(89012,_,_,_,_,_)::(
     findall(A, (aluno(89012,A,X,_,_,_), not(nulo(X))), R),
     length(R,0)
 ).

% Representar o falso
-aluno(Id,N,C,A,E,P):-
    not(aluno(Id,N,C,A,E,P)),
    not(exception(aluno(Id,N,C,A,E,P))).

-par(1).

% Representar valores nulos do tipo interdito
nulo(interdito).

% Excepcoes
exception(aluno(I,_,C,A,E,P)):- aluno(I,nulo,C,A,E,P).
exception(aluno(I,N,_,A,E,P)):- aluno(I,N,nulo,A,E,P).
exception(aluno(I,N,_,A,E,P)):- aluno(I,N,incerto,A,E,P).
exception(aluno(I,N,_,A,E,P)):- aluno(I,N,interdito,A,E,P).
exception(aluno(I,N,C,_,E,P)):- aluno(I,N,C,incerto,E,P).
exception(aluno(I,N,C,A,_,P)):- aluno(I,N,C,A,incerto,P).
exception(aluno(I,N,C,A,_,P)):- aluno(I,N,C,A,nulo,P).
exception(aluno(I,N,C,A,E,_)):- aluno(I,N,C,A,E,incerto).
exception(aluno(3456,carlos,mdi,1,Ects,sim)):-
    Ects < 45, (-1) < Ects .
exception(aluno(45678,duarte,miec,A,180,sim)):-
    A < 6, 2 < A.
exception(aluno(67890,filipe,C,1,E,nao)):-
    ( C == lfis ; C == lefis ),
    ( E == 45 ; E == 54).
exception(aluno(78901,N,lcc,3,180,sim)):-
    N == gisela ; N == gisele; N == gabriel.

% Conhecimento
aluno(12345,ana,lei,1,60,sim).
aluno(23456,beatriz,lcc,2,60,nao).
aluno(56789,eva,miec,4,240,incerto).
aluno(89012,heitor,interdito,1,10,nao).
aluno(90123,ivo,incerto,2,180,sim).

%%%%%%%%% funcoes auxiliares %%%%%%%%%%%%%%%%%%
%% Testar conhecimento
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

%% Adicionar um novo aluno
add_aluno(Id,N,C,A,E,P):- new_predicado(aluno(Id,N,C,A,E,P)).

% Para testar a questao 2
par(0).
par(succ(succ(X))):- par(X).
