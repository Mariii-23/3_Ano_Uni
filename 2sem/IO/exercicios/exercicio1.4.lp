//x2 -> segunda feira
//x3 -> terca feira
//x4 -> quarta feira
//x5 -> quinta feira
//x6 -> sexta feira
//x7 -> sabado feira
//x8 -> domingo feira

min: 5 x2 + 5.5 x3 + 6.5 x4 + 6.5 x5 + 6.5 x6 + 6.5 x7 + 6 x8;

segunda: x2 + x8 + x7 + x6 + x5 >= 10;
terca:   x3 + x2 + x8 + x7 + x6 >= 16;
quarta:  x4 + x3 + x2 + x8 + x7 >= 12;
quinta:  x5 + x4 + x3 + x2 + x1 >= 8 ;
sexta:   x6 + x5 + x4 + x3 + x2 >= 15;
sabado:  x7 + x6 + x5 + x4 + x3 >= 18;
domingo: x8 + x7 + x6 + x5 + x4 >= 12;

int x2, x3, x4, x5, x6, x7, x8;
