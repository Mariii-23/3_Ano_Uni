#!/usr/bin/env python3
import ply.yacc as yacc
from calc_lex import tokens, literals

def p_Comandos_Lista(p):
    "Comandos : Comandos Comando"

def p_Comandos_Simples(p):
    "Comandos : Comando"

def p_Comandos_Atrib(p):
    "Comando : id '=' Exp"
    p.parser.registos[p[1]] = p[3]

def p_Comandos_Escrita(p):
    "Comando : '!' Exp"
    print(p[2])

def p_Comandos_Leitura(p):
    "Comando : '?' id"
    valor = input("Introduza um valor inteiro: ")
    p.parser.registos[p[2]] = int(valor)

def p_Comandos_Dump(p):
    "Comando : DUMP"
    print(p.parser.registos)

def p_Exp_add(p):
    "Exp : Exp '+' Termo"
    p[0] = p[1] + p[3]

def p_Exp_sub(p):
    "Exp : Exp '-' Termo"
    p[0] = p[1] - p[3]

def p_Exp_Termo(p):
    "Exp : Termo"
    p[0] = p[1]

def p_Termo_mul(p):
    "Termo : Termo '*' Fator"
    p[0] = p[1] * p[3]

def p_Termo_div(p):
    "Termo : Termo '/' Fator"
    if p[3] != 0 :
        p[0] = p[1] / p[3]
    else:
        print("Erro: divisao por 0...")
        p[0] = 0

def p_Termo_Fator(p):
    "Termo : Fator"
    p[0] = p[1]

def p_Fator_grupo(p):
    "Fator : '(' Exp ')'"
    p[0] = p[2]

def p_Fator_num(p):
    "Fator : num"
    p[0] = p[1]

def p_Fator_id(p):
    "Fator : id"
    p[0] = p.parser.registos[p[1]]

def p_error(p):
    print("Erro sintatioco:, ",p)
    parser.success = False


# Build the parser
parser = yacc.yacc()

parser.registos = {}

# Read line from input and parse it
import sys
for linha in sys.stdin:
    parser.success = True
    parser.parse(linha)
    if parser.success:
        print("Calc : ", linha)
    else:
        print("Erro")
