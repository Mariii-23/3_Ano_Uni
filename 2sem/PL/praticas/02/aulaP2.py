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
# -> o separador tem q ser sempre o mesmo
def matriculas_de_outro_mundo():
    f = open("matriculas.txt", "r")

    p = r'(\d\d-){3}|(\d\d:){3}|(\d\d...){3}\d\d'
    # p = r'\d\d(?<foo>(\.\.\.|-|:))\d\d\k<foo>\d\d\k<foo>\d\d'
    matriculas = 0
    for linha in f.readlines():
        lista = re.findall(p,linha)
        if lista:
            matriculas+=len(lista)
    print("\n\nMatriculas válidas: ", matriculas)
    return matriculas

matriculas_de_outro_mundo()

def coordenadas():
    coordenadas = r'\([+\-]?[1-9]?\d(\.\d+)?, [+\-]?(1[0-8]\d|[1-9]?\d)(\.\d+)?\)'
    p = re.compile(coordenadas)
    f = open("coordenadas.txt", "r")
    for linha in f.readlines():
        # coisas e tal
        # TODO
        print("LALALALALA")
    return 0

# Somador on/of:
# Criar um programa que analisa um texto e:
# 1. Sempre que apanhar um inteiro soma-o
# 2. Sempre que apanhar a palavra "off", para de somar,
# pode ser maiusculas ou minusculas
# 3. Sempre que apanhar a palavra "on", volta a somar
# pode ser maiusculas ou minusculas
# 4. Sempre que apanhar "=" imprime o valor da soma
# 5. Quando chegar ao fim do ficheiro imprime o valor da soma
def somador():
    estado = true
    soma = 0
    f = open("coordenadas.txt", "r")

    # on  -> ([Oo][nN])
    # off -> ([Oo][fF]{2})
    # num -> (\d*)
    # =   -> (=)
    re.finditer(r'([Oo][nN])|([Oo][fF]{2})|(\d*)|(=)')
    return soma
