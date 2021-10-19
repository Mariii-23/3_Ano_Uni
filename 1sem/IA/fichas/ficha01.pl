filho(joao,jose).
filho(jose,manuel).
filho(manuel,tiago).
filho(tiago,afonso).
filho(carlos,jose).
filho(filipe,paulo).
filho(maria,paulo).

pai(P,F):- filho(F,P).
irmao(F1,F2,P):- pai(P,F1), pai(P,F2).

avo(antonio,nadia).
avo(ana,nuno).

avo(A,N):- filho(X,A),filho(N,X).
avo(A,N):- neto(N,A).

descende(X,Y):- filho(X,Y).
descende(X,Y):- filho(X,A),descende(A,Y).

grau(X,Y,1):- filho(X,Y).
grau(X,Y,G):- filho(X,A), grau(A,Y,N), N is N+1.

grau(X,Y,2):- avo(X,Y).
grau(X,Y,3):- bisavo(X,Y).
grau(X,Y,4):- trisavo(X,Y).

sexo(joao,masculino).
sexo(jose,masculino).
sexo(maria,feminino).
sexo(joana,feminino).
