#!/usr/bin/env python3

#tokenizer para a maquina de calcular
import ply.lex as lex

tokens = ["PA","PF"]

def t_PA(t):
    r'\('
    t.lexer.count += 1;
    return t

def t_PF(t):
    r'\)'
    t.lexer.count -= 1;
    if t.lexer.count < 0:
        print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
        t.lexer.skip(1)
    else:
        return t

t_ignore = '\n\t '

def t_error(t):
    print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
    t.lexer.skip(1)

lexer = lex.lex()
lexer.count = 0;

import sys

for line in sys.stdin:
    lexer.count = 0;
    lexer.input(line)
    for tok in lexer:
        print(tok)
