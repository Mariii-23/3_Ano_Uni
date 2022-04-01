#!/usr/bin/env python3
import ply.lex as lex
tokens = ['id','num','DUMP']
literals = ['+','-','*','/','(',')','=','?','!']
#DUMP a maisculo para distinguir dos outros: pals reservaadas

t_id = r'[_a-zA-Z]\w*'
t_DUMP = r'!!'

# o num tem de ser transformado num inteiro
def t_num(t):
    r'\d+'
    t.value = int(t.value)
    return t

t_ignore = "\t\n "

def t_error(t):
    print('Caráter ilegal:',t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()
