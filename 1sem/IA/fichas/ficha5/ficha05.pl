% Questao 1
% Problema de estado único.

%------------------------------------------------
% Questao 2
%
% Conjunto de Estados: Conjunto de pares (X,Y), X representa o número de litros contidos no jarro de 8 litros de capacidade e o Y representa o número de litros contids no jarro de 5 litros de capacidade

% Estado inicial: Jarros com 0 litros (0,0)
inicial(jarros(0,0)).

% Estado objetivo: Um dos jarros com 4 l (_,4) ou (4,_)
final(jarros(_,4)).
final(jarros(4,_)).

% Operadores: esvaziar um balde, encher um balde, esvaziar um balde para outro (nunca podendo ultrapassar a capacidade máxima do balde que se esta a encher ou ate que o outro balde fique vazio).
transicao(jarros(V1,V2),encher(1),jarros(8,V2)):- V1<8.
transicao(jarros(V1,V2),encher(2),jarros(V1,5)):- V2<8.

transicao(jarros(V1,V2),encher(1,2),jarros(NV1,NV2)):-
  V1 > 0,
  NV1 is max(V1 -5 +V2, 0),
  NV1 < V1,
  NV2 is V2 + V1 - NV1.

transicao(jarros(V1,V2),encher(2,1),jarros(NV1,NV2)):-
  V2 > 0,
  NV2 is max(V2 -8 +V1, 0),
  NV2 < V2,
  NV1 is V1 + V2 - NV2.

transicao(jarros(V1,V2),vazio(1),jarros(0,V2)):- V1>0.
transicao(jarros(V1,V2),vazio(2),jarros(V1,0)):- V2>0.

%------------------------------------------------
% Questao 3

%%%%%%%%%%%%%%%%%%%
% Depth first
dfs2(X, X, Cam, S):- reverse(Cam,S).
dfs2(X, Y, Cam, S):-
  transicao(X, _, Novo),
  \+ member(Novo, Cam),
  dfs2(Novo, Y, [Novo|Cam], S).

dfs(X, Y, S):-  dfs2(X, Y, [X], S).

%%%%%%%%%%%%%%%%%%%
% Breadth first

bfs(Orig, Dest, Cam):- bfs2(Dest,[[Orig]],Cam).

bfs2(X,[[X|T]|_],Solucao)  :- reverse([X|T],Solucao).

bfs2(EstadoF,[EstadoA|Outros],Solucao) :-
    EstadoA = [Act|_],
    findall([X|EstadoA],
            (EstadoF\==Act,
            transicao(Act,_,X),
            not(member(X,EstadoA))),
            Novos),
    append(Outros,Novos,Todos),
    bfs2(EstadoF,Todos,Solucao).

%%%% Testes
resolf(S):- dfs(jarros(0,0), jarros(_,4), S).
resolf1(S):- dfs(jarros(0,0), jarros(4,_), S).

resolf2(S):- bfs(jarros(0,0),jarros(4,_),S).
resolf3(S):- bfs(jarros(0,0),jarros(_,4),S).
