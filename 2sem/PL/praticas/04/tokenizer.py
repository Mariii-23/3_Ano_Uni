#!/usr/bin/env python3

#tokenizer para a maquina de calcular
import ply.lex as lex

tokens = ["PA","PF","NUM","ID","OP"]

t_PA = r'\('
t_PF = r'\)'
t_OP = r'[+*\-\/]'

def t_NUM(t):
    r'\d+'
    t.value = int(t.value)
    return t

def t_ID(t):
    r'[a-zA-Z_]\w*'
    return t

t_ignore = '\n\t'

def t_error(t):
    print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
    t.lexer.skip(1)

lexer = lex.lex()

import sys

for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        print(tok)
