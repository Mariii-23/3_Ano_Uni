// x1 -> combinacao 1 3
// x2 -> combinacao 1 4
// x3 -> combinacao 2 3
// x4 -> combinacao 2 4
// x5 -> combinacao 2 2
// x6 -> combinacao 3 3
// x7 -> combinacao 3 4 4
// x8 -> combinacao 4 4 4

min: x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8;

encomenda1: x1 + x2 >= 50;
encomenda2: x3 + x4 + 2 x5 >= 200;
encomenda3: x1 + x3 + 2 x6 + x7 >= 400;
encomenda4: x2 + x4 + 2 x7 + 3 x8 >= 100;

int x1, x2, x3, x4, x5, x6, x7, x8;
