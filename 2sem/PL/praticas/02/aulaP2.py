#!/usr/bin/env python3
import sys
import re

# Read all lines in the file
def readWithFor():
    n = 1
    for linha in sys.stdin:
        print(n, ": ", linha, end="")
        n = n + 1
    return 1

def readWithWhile():
    n = 1
    f = sys.stdin
    linha1 = f.readline()
    nlinhas = int(linha1)
    while nlinhas > 0:
        nlinhas -= 1
        linha = f.readline()
        print(n, ": ", linha, end="")
        n = n + 1
    return 1

# readWithFor()
# readWithWhile()

# Exercicio
# Se apanhar "Ola" no início da linha: escreve "1"
#   r'^ola'
# Se apanhar "Ola" no fim da linha: escreve "2"
#   r'ola$'
# Se apanhar uma linha contendo apenas "Ola": escreve "0"
#   r'^ola$'
# Nos outros casos, escreve "-1"

def matchOla():
    f = sys.stdin
    linha1 = f.readline()
    nlinhas = int(linha1)
    while nlinhas > 0:
        nlinhas -= 1
        linha = f.readline()
        # deve se sempre colocar as expressoes mais
        # detalhadas primeiro
        if re.search(r'^Ola$',linha):
            print("0")
        elif re.search(r'Ola$',linha):
            print("2")
        elif re.search(r'^Ola',linha):
            print("1")
        else:
            print("-1")

    return 1
# matchOla()

def problema1AlienUsername():
    f = open("alien.txt", "r")
    p = re.compile(r'^(_|\.)\d+[a-zA-Z]{3,}_?$')
    for linha in f.readlines():
        if p.match(linha):
            print("Válido")
        else:
            print ("Inválido")
    return 0

problema1AlienUsername()

# Problema 4
# -> uma matricula tem 8 digitos
#
# -> os 8 digitos estao divididos em 4 partes iguais
# de 2 digitos por um separador ... : -
#
# ->
def matriculas_de_outro_mundo():
    print("\nMatriculas válidas")
    f = open("matriculas.txt", "r")

    p = re.compile(r'\d\d(\.\.\.|-|:)\d\d\3\d\d\3\d\d')
    for linha in f.readlines():
        if p.match(linha):
            print("Válido")
        else:
            print ("Inválido")
    return 0

matriculas_de_outro_mundo()
