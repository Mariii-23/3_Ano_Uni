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

goal(t).

%% caminho mais curto

resolve_aestrela(Nodo,Caminho/Custo):-
  estima(Nodo,Estima),
  a_estrela([[Nodo]/0/Estima],InvCaminho/Custo/_),
  inverso(InvCaminho,Caminho).

a_estrela(Caminhos,Caminho):-
    obtem_caminho(Caminhos,Caminho),
    Caminho=[Nodo|_]/_/_,
    goal(Nodo).

%% outra coisa... MelhorCaminho?

obtem_caminho([Caminho],Caminho):- !.

obtem_caminho([Caminho1/Custo1/Estima1,Caminho2/Custo2/Estima2/Caminhos],MCam):-
    Custo1+Estima1=<Custo2+Estima2, !,
    obtem_caminho([Caminho1/Custo1/Estima1/Caminhos],MCam).

obtem_caminho([_|Caminhos],MCam):-
    obtem_caminho(Caminhos,MCam).

seleciona(E,[E|Xs],Xs).
seleciona(E,[E|Xs],[X|Ys]):-
    seleciona(E,Xs,Ys).

expande_aestrela(Caminho,ExpCam):-
    findall(NovoCaminho,adjacente(Caminho,NovoCaminho),ExpCam).

adjacente([Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCaminho/Estima):-
    move(Nodo,ProxNodo,CustoPasso),
    \+ member(ProxNodo,Caminho),
    NovoCaminho is Custo+CustoPasso,
    estima(ProxNodo,Estima).

a_estrela1(Caminhos,SCaminho):-
    obtem_caminho(Caminhos,MelhorCaminho),
    seleciona(MelhorCaminho,Caminhos,OutrosCam),
    expande_aestrela(MelhorCaminho,ExpCam),
    append(OutrosCam,ExpCam,NCam),
    a_estrela1(NCam,SCaminho).
