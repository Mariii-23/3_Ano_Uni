// variaveis
// x1 -> Arroz de grao longo cozido
// x2 -> Atum
// x3 -> Azeite
// x4 -> Brocolos

/* Objective function */
min: 0.10 x1 + 1.20 x2 + 0.06 x3 + 0.24 x4;

/* Constraints */
proteina1: 2.5 x1 + 20 x2 + 3 x4 >= 15;
proteina2: 2.5 x1 + 20 x2 + 3 x4 <= 10;

lipidos1: 0.2 x1 + 8 x2 + 12 x3 + 0.3 x4 <= 30;
lipidos2: 0.2 x1 + 8 x2 + 12 x3 + 0.3 x4 >= 15;

hidratos1: 28 x1 + 5.5 x4 <= 75;
hidratos2: 28 x1 + 5.5 x4 >= 55;

energia1: 123.8 x1 + 152 x2 + 108 x3 + 36.7 x4 <= 700;
energia2: 123.8 x1 + 152 x2 + 108 x3 + 36.7 x4 >= 600;
