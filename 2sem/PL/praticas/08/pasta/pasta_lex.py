#!/usr/bin/env python3
import ply.lex as lex
tokens = ['id']
literals = ['[',']','(',')']

t_id = r'\"[^\"]+\"'

t_ignore = "\t\n "

def t_error(t):
    print('Caráter ilegal:',t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()
