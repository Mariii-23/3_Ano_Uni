#!/usr/bin/env python3
import ply.lex as lex

tokens = ['z','u']
literals = ['0','1']
# literals = "01"

t_z = r'0'
t_u = r'1'

t_ANY_ignore = "\t\n "

def t_error(t):
    print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
    t.lexer.skip(1)

lexer = lex.lex()
