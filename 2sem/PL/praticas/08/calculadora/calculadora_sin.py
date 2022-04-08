#!/usr/bin/env python3

# (* 2 3 (+ 1 1)) => 12
# (+ 1 2 (* 1 2 3) (+ 11 11 2 3)) => 36
# 27 => 27
# (+ 1 2 3 4 5 6 7 8) => 36

import ply.yacc as yacc
from calculadora_lex import tokens, literals

def p_Calculadora(p):
    "Calculadora : Exp"
    print(p[1])

def somatorio(lista):
    if len(lista) == 0:
        return 0
    return lista[0] + somatorio(lista[1:])

def p_Exp_add(p):
    "Exp : '(' '+' Lista Exp ')'"
    p[0] = somatorio(p[3]) + p[4]

def produtorio(lista):
    if len(lista) == 0:
        return 1
    return lista[0] * produtorio(lista[1:])

def p_Exp_mul(p):
    "Exp : '(' '*' Lista Exp ')'"
    p[0] = produtorio(p[3]) * p[4]

def p_Exp_num(p):
    "Exp : num"
    p[0] = p[1]

def p_Lista(p):
    "Lista : Lista num"
    p[0] = p[1] + [p[2]]

def p_Lista_num(p):
    "Lista : num"
    p[0] = [p[1]]

def p_error(p):
    print("Erro sintatioco:, ",p)
    parser.success = False

# Build the parser
parser = yacc.yacc()

# Read line from input and parse it
import sys
for linha in sys.stdin:
    parser.success = True
    parser.parse(linha)
    if parser.success:
        print("Calc : ", linha)
