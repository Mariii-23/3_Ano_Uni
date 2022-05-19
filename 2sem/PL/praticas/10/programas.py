#!/usr/bin/env python3
import ply.lex as lex
literals = ['=','(',')', ',' ]
tokens = ['INT', 'INPUT','STR','PRINT', 'id', 'str']

def t_INT(t):
    r'int'
    return t

def t_INPUT(t):
    r'input'
    return t

def t_STR(t):
    r'str'
    return t

def t_PRINT(t):
    r'print'
    return t

def t_id(t):
    r'[a-zA-Z_]\w*'
    return t

def t_str(t):
    r'\"[^\"]*\"'
    return t

t_ignore = "\t\n "

def t_error(t):
    print('Caráter ilegal:',t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()
