//x1-> apartamentos t2
//x2-> apartamentos t3

/* Objective function */
min: 150000 x1 + 200000 x2;

/* Constraints */
t2: x1 + 4 x2 >= 5;
t3: 3 x1 + 2 x2 >= 8;

int x1,x2;
