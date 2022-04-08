#!/usr/bin/env python3
import ply.lex as lex
tokens = ['num']
literals = ['+','-','*','/','(',')','!']

def t_num(t):
    r'\d+'
    t.value = int(t.value)
    return t

t_ignore = "\t\n "

def t_error(t):
    print('Caráter ilegal:',t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()
