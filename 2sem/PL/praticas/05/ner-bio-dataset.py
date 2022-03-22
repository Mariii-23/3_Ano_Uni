#!/usr/bin/env python3
import ply.lex as lex
import re
import sys

states = [('FEATURE', 'exclusive')]

tokens = ["B","O","I"]

def t_ANY_error(t):
    t.lexer.skip(1)

def t_B(t):
    r'B-.+'
    conteudo = t.value[2:]
    partes = re.split(r'\s+',conteudo)
    t.lexer.tipo = partes[0]
    t.lexer.buffer = partes[1]
    t.lexer.begin('FEATURE')

def t_O(t):
    r'O.+'
    pass

def t_I(t):
    r'I-.+'
    pass

def t_FEATURE_O(t):
    r'O.+'
    if t.lexer.tipo in t.lexer.features.keys():
        t.lexer.features[t.lexer.tipo].append(t.lexer.buffer)
    else:
        t.lexer.features[t.lexer.tipo] = [t.lexer.buffer]
    t.lexer.begin('INITIAL')

def t_FEATURE_I(t):
    r'I-.+'
    partes = re.split(r'\s+',t.value)
    t.lexer.buffer += " " + partes[1]
    pass

def t_FEATURE_B(t):
    r'B-.+'
    if t.lexer.tipo in t.lexer.features.keys():
        t.lexer.features[t.lexer.tipo].append(t.lexer.buffer)
    else:
        t.lexer.features[t.lexer.tipo] = [t.lexer.buffer]
    conteudo = t.value[2:]
    partes = re.split(r'\s+',conteudo)
    t.lexer.tipo = partes[0]
    t.lexer.buffer = partes[1]

t_ANY_ignore = "\n"

lexer = lex.lex()
lexer.tipo = ""
lexer.conteudo = ""
lexer.features = {}


for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        print(tok)

print(lexer.features)
