:- dynamic '-'/1.
:- dynamic servico/2.
:- dynamic ato/4.
:- dynamic enfermeiro/1.
:- op(900,xfy,'::').


+servico(_,_) :: (
         findall(S1, (servico(S1,zulmira),not(nulo(S1))) ,R),
         length(R,0)).

+ato(_,_,_,_) :: (
         findall(A, ato(A,_,_,feriado) ,R),
         length(R,0)).

-servico(S,E):-
    not(servico(S,E)),
    not(exception(servico(S,E))).

-ato(A,E,U,D):-
    not(ato(A,E,U,D)),
    not(exception(ato(A,E,U,D))).


%%-enfermeiro(N) :: (
%%    findall(N, ato(N,_,_,_) ,R),
%%    length(R,0)).

% TODO trocar para remover servico
-enfermeiro(N) :: (
    findall(N, servico(_,N) ,R),
    length(R,0)).

exception(servico(_,E)):- servico(incerto,E).
exception(servico(_,E)):- servico(interdito,E).
exception(ato(_,E,U,D)):- ato(incerto,E,U,D).
exception(ato(A,_,U,D)):- ato(A,incerto,U,D).
exception(ato(A,E,_,D)):- ato(A,E,incerto,D).
exception(ato(A,E,U,_)):- ato(A,E,U,incerto).
exception(ato(A,E,_,_)):- ato(A,E,incerto,incerto).
exception(ato(penso,ana,jacinta,D)):-
    D==segunda;
    D==terca;
    D==quarta;
    D==quinta;
    D==sexta.
exception(ato(sutura,maria,josefa,D)):-
    D==terca;
    D==sexta.
exception(ato(sutura,mariana,josefa,D)):-
    D==terca;
    D==sexta.

enfermeira(amelia).
enfermeira(ana).
enfermeira(maria).
enfermeira(mariana).
enfermeira(sofia).
enfermeira(teodora).
enfermeira(zulmira).

servico(ortopedia,amélia).
servico(obstetrícia,ana).
servico(obstetrícia,maria).
servico(obstetrícia,mariana).
servico(geriatria,sofia).
servico(geriatria,susana).
servico(incerto,teodora).
servico(interdito,teodora).

ato(penso,ana,joana,sabado).
ato(gesso,amélia,josé,domingo).
ato(incerto,mariana,joaquina,domingo).
ato(domicilio,maria,incerto,incerto).
ato(domicilio,susana,joao,segunda).
ato(domicilio,susana,jose,segunda).
ato(sutura,incerto,josue,segunda).

nulo(interdito).

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

%% inserir coisas
new_servico(S,E):- not(servico(S,E)) ,new_predicado(servico(S,E)).
new_ato(A,E,U,D):- not(ato(A,E,U,D)) , new_predicado(ato(A,E,U,D)).

remove_enfermerio(N):-  remover_predicado(enfermeiro(N)).
