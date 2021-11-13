

aresta(a,b,2).
aresta(b,c,2).
aresta(c,d,3).
aresta(d,t,3).
aresta(t,g,2).
aresta(g,f,2).
aresta(f,e,5).
aresta(e,s,2).
aresta(s,a,2).

aresta(X,Y,C):- aresta(Y,X,C);

estima(a,5).
estima(b,4).
estima(c,4).
estima(d,3).
%% ------- estima(t,0).
estima(g,2).
estima(f,4).
estima(e,7).
estima(s,10).


%% procura um caminho qualquer
caminho2(I,I,N,S):- reverse(N,S).

caminho2(I,F,Cam,S):-
  aresta(I,Novo,_),
  \+ member(Novo,Cam),
  caminho2(Novo,F,[Novo|Cam],S).

caminho(I,F,S):- caminho2(I,F,[I],S).


%% caminho mais curto

%% curtoAux(I,I,C,Cam,C,S):-reverse(Cam,S).
%%
%% curtoAux(I,F,C1,Cam,C,S):-
%%   estima(I,Estima),
%%   curtoAux()
%%
%%
%% cCurto(I,F,C,S):-
%%   curtoAux(I,F,0,[I],C,S).


resolve_aestrela(Nodo,Caminho/Custo):-
  estima(Nodo,Estima),
  a_estrela([[Nodo]/0/Estima],InvCaminho/Custo/),
  reversse(InvCaminho,Caminho).
