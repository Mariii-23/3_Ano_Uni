#!/usr/bin/env python3

#tokenizer para a maquina de calcular
import ply.lex as lex

tokens = ["ON","OFF","NUM","PRINT", "LETTERS"]

t_LETTERS=r'[a-zA-Z]+'

def t_ON(t):
    r'[Oo][Nn]'
    t.lexer.on = True;
    return t

def t_OFF(t):
    r'[Oo][fF]{2}'
    t.lexer.on = False;
    return t

def t_NUM(t):
    r'\d+'
    if t.lexer.on:
        t.lexer.count += int(t.value)
    return t

def t_PRINT(t):
    r'\='
    print("COUNT:: ",t.lexer.count)
    return t

t_ignore = '\n\t '

def t_error(t):
    print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
    t.lexer.skip(1)

lexer = lex.lex()

import sys

lexer.count = 0;
lexer.on = True

for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        print(tok)
