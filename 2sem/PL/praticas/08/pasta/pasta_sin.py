#!/usr/bin/env python3

# [ "ola" [ "other" ( "ficheiro.txt" "kjasdf" ) ] ( "file2.txt" "asdfdsa" ) ]
# [ "folder1" ("file.txt" "path" ) ["folder2" [ "folder3"] ["folder3_1" ("file3.txt" "path") ("file3_1.png" "path")]]]
# [ "wall" ( "hihihi.png" "/home/mari/wallpaper/web-room.png")]
# FIXME o exemplo de baixo da mal
# [ "wall" ("hihih.png" "/home/mari/wallpaper/web-room.png" ) ["aulas" [ "nada_aqui"] ["PL" ("regex.org" "/home/mari/3_Ano_Uni/2sem/PL/regex.org") ("ficheiro_nao_existe.txt" "path")]]]

import re
import os
import shutil
from pathlib import Path
import ply.yacc as yacc
from pasta_lex import tokens, literals

def p_Z(p):
    "Z : Pasta"

def p_pasta(p):
    "Pasta : '[' id Lista ']'"
    express = r'\"([^\"]+)\"'
    r = re.compile(express)
    name = ""
    tokens = r.finditer(p[2])
    for t in tokens:
        name = t.group(1)
    os.mkdir(name)
    print("Folder created: " + name)
    print("Files on folder: ", end="")
    for i in p.parser.itens:
        # mover os itens
        shutil.move(i, name)
        print(i, sep=" , ", end=",")

    # limpar os itens
    # acrescentar a pasta a itens
    p.parser.itens = [name]
    print("\n")

def p_Lista_Pasta(p):
    "Lista : Lista Pasta"

def p_Lista_Ficheiro(p):
    "Lista : Lista Ficheiro"

def p_Lista_Vazia(p):
    "Lista : "

def p_Ficheiro(p):
    "Ficheiro : '(' id id ')'"
    # express = r'\"([^\"]+)\" \"([^\"]+)\"'
    express = r'\"([^\"]+)\"'
    r = re.compile(express)
    name = ""
    path = ""
    tokens = r.finditer(p[2])
    for t in tokens:
        name = t.group(1)

    tokens = r.finditer(p[3])
    for t in tokens:
        path = t.group(1)

    p.parser.itens.append(name)
    # copiar o ficheiro ou apenas cria lo
    try:
        shutil.copyfile(path,name)
        print("File copied: " , path ," to name: ", name)
    except:
        Path(name).touch()
        print("File created: " + name )

def p_error(p):
    print("Erro sintatico:, ",p)
    parser.success = False

# Build the parser
parser = yacc.yacc()

parser.itens = []

# Read line from input and parse it
import sys
for linha in sys.stdin:
    parser.success = True
    parser.parse(linha)
    if parser.success:
        print("Done")
