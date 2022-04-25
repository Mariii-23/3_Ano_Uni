#!/usr/bin/env python3

import ply.lex as lex

literals = ['+','-','*','/','(',')','.']
tokens = ('num', 'id')

t_num = r'\d+'
t_id = r'[A-Za-z_]\w*'

# define a rule so we can track line numbers
def t_new_line(t):
    r'\n+'
    t.lexer.lineno += len(t.value)

t_ignore = "\t "

def t_error(t):
    print("Erro léxico no token '%s" %  t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()
####
prox_simb = ('Erro', '',0,0)

def parserError(simb):
    print("Erro sintático: ", simb)

def rec_term(simb):
    global prox_simb
    if prox_simb.type == simb:
        prox_simb = lexer.token()
    else:
        parserError(simb)

def rec_Fator():
    global prox_simb
    if prox_simb.type == 'id':
        rec_term('id')
    elif prox_simb.type == 'num':
        rec_term('num')
    elif prox_simb.type == '(':
        rec_Exp()
        rec_term(')')
        pass
    else:
        parserError(prox_simb)

def rec_Termo2():
    global prox_simb
    if prox_simb.type == '*':
        rec_term('*')
        rec_Termo()
    elif prox_simb.type == '/':
        rec_term('/')
        rec_Termo()
    elif prox_simb.type in ['.',')','+','-']:
        pass
    else:
        parserError(prox_simb)

def rec_Termo():
    rec_Fator()
    rec_Termo2()

def rec_Exp2():
    global prox_simb
    if prox_simb.type == '+':
        rec_term('+')
        rec_Exp()
    elif prox_simb.type == '-':
        rec_term('-')
        rec_Exp()
    elif prox_simb.type in ['.',')']:
        pass
    else:
        parserError(prox_simb)

def rec_Exp():
    rec_Termo()
    rec_Exp2()

def rec_Calc():
    rec_Exp()
    rec_term('.')

def rec_Parser(data):
    global prox_simb
    lexer.input(data)
    prox_simb = lexer.token()
    rec_Calc()
    print("That's the end...")

linha = input("Introduza: ")
rec_Parser(linha)
