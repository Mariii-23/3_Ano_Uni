soma(X,Y,Soma):- Soma is X + Y.
soma3(X,Y,Z,Soma3):-Soma3 is X + Y + Z.
conta(X,Y,+,Conta):-Conta is X + Y.
conta(X,Y,-,Conta):-Conta is X - Y.
conta(X,Y,*,Conta):-Conta is X * Y.
    conta(X,Y,/,Conta):-Conta is X / Y.

max(X,Y,Maximo):-Maximo is max(X,Y).
max3(X,Y,Z,Maximo):-Maximo is max( X, max(Y,Z) ).

min(X,Y,Minimo):- Minimo is min(X,Y).
min3(X,Y,Z,Minimo):-Minimo is min(X,min(Y,Z)).

isPar(X):-mod(X,2) =:= 0.
isImpar(X):-mod(X,2) =\= 0.

mdc(X,X,X).
mdc(X,Y,D):- X < Y, Y1 is Y-X, mdc(X,Y1,D).
mdc(X,Y,D):- X > Y, mdc(Y,X,D).
mmc(X,Y,D):- mdc(X,Y,MDC), D is X * Y / MDC.
