#!/usr/bin/env python3

import ply.yacc as yacc
from abin_lex import tokens

def p_gramatica(p):
    """
    ABin : PA PF
         | PA NUM ABin ABin PF
    """

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
