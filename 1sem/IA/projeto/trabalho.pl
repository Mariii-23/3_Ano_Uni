
:- op(900,xfy,'::').

:- dynamic transporte/5.
:- dynamic estafeta/2.
:- dynamic cliente/3.
:- dynamic encomenda/6.
:- dynamic servico/6.


:- include('bases_dados.pl').

:- include('funcoes_auxiliares.pl').
:- include('writeStructs.pl').

%%%%%%%%%%%%%%%%%%%% Validar dados %%%%%%%%%%%
%%%  Transportes  %%%%

% Garantir que o id dos transportes é único
+transporte(Id,_,_,_,_) :: (find_solucoes(Id,transporte(Id,_,_,_,_), R),
                            len(R,1)).

+transporte(_,N,_,_,_) :: (find_solucoes(N,transporte(_,N,_,_,_), R),
                            len(R,1)).

% Garantir que um transporte só pode ser removido no caso de não estar presente em nenhum serviço
-transporte(Id,_,_,_,_) :: (find_solucoes(Id,servico(_,_,_,Ids,_,_),R),
                            len(R,0)
                           ).


% -------- Adicionar predicados ---
newTransporte(Id,N,V,C,E):- new_predicado(transporte(Id,N,V,C,E)).

% -------- Remover predicados ---
removeTransporte(Id):- transporte(Id,N,V,C,E) , remover_predicado( transporte(Id,N,V,C,E)).
