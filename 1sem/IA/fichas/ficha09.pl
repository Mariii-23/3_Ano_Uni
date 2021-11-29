
:- dynamic -\1.

-voo(X):- nao(voo(X)),
          nao(excecao(-voo(X))).

-voo(X):- mamifero(X),
          nao(excecao(-voo(X))).

voo(X):- ave(X).

excecao(-voo(X)):- morcego(X).
excecao(+voo(X)):- pinguim(X).
