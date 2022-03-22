#!/usr/bin/env python3
#retirar comentarios de um ficheiro html
import ply.lex as lex
import sys

states = [('COM', 'exclusive')]

tokens = ["BCOM","TEXT"]


def t_BCOM(t):
    r'<!--'
    t.lexer.begin('COM')

def t_TEXT(t):
    r'.|\n'
    print(t.value, end="")

def t_COM_ECOM(t):
    r'-->'
    t.lexer.begin('INITIAL')

def t_COM_TEXT(t):
    r'.|\n'
    pass

t_ignore = ''

lexer = lex.lex()

import sys

for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        print(tok)
