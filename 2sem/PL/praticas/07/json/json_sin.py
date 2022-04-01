#!/usr/bin/env python3

import ply.yacc as yacc
from json_lex import tokens

def p_S(p):
    "S : Abin"
    print(p[1])

def p_Abin_vazio(p):
    "Abin : PA PF"
    p[0] = "null"

def p_Abin(p):
    "Abin : PA NUM Abin Abin PF"
    p[0] = "{\n"
    p[0] += "\t\"root\": " + p[2] + ",\n"
    p[0] += "\t\"left\": " + p[3] + ",\n"
    p[0] += "\t\"right\": " + p[4] + "\n"
    p[0] += "}\n"

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
        print("Fráse válida: ", linha)
    else:
        print("Fráse inválida")
