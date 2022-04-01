import ply.yacc as yacc
from capicua_lex import tokens

def p_gramatica(p):
    """
    Capicua : z Capicua z
            | u Capicua u
            | z
            | u
            |
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
        print("Capicua : ", linha)
    else:
        print("Não é capicua")
