#!/usr/bin/env python3

import sys
import re


def wc_command():
# pal = r'\b\w+\b'
    pal = r'\b\w+\b'
    ppal = re.compile(pal)

    chars = 0
    pals = 0
    linhas = 0

    for line in sys.stdin:
        pals += len(ppal.findall(line))
        linhas += 1
        chars +=  len(line)

        print(f'Linhas: {linhas}  palavras: {pals}   chars: {chars}')

    return;

 # 'user@dom.top-dom'
 # user :: alfanumericos ou alguns caracteres especiais:  '.' '-',e tc
 # o primeiro e ultimo carater nao pode ser um carater especial
 # nao pode haver 2 carateres especiais consecutivos
 # dom :: alfanumericos e hifen;
 # top-dom :: alfanumericos e hifen com 2 ou mais caracteres
def emailValid():
    f = open("emails.txt", "r")
    email = r'^(\w+[\-.]?\w+)+@(\w+[\-]?\w+\.)+(\w+[\-]?\w+)+$'
    pEmail = re.compile(email)
    print("Emails")
    for (n,linha) in enumerate(f.readlines()):
        if pEmail.search(linha):
            print(n, ' :: ', "Válido :: ", linha) # , ' :: ', pEmail.groups())
        else:
            print(n, ' :: ', "Inválido :: ", linha)

emailValid()

# apanhar todos os links q se encontrem nos "href=" nos ficheiros html
def links():
    print("Links valids\n")
    url = r'(?i)href\s*=\s*"([^"]*)"'
    pUrl = re.compile(url)
    f = open("urls.txt", "r")

    for linha in f.readlines():
        enderecos = pUrl.finditer(linha)
        for e in enderecos:
            print(e.group(1))

links()

# tokenizer de expressões  com ints ou operações
# 5 + 3 * 21
# Int = 5
# Opt = +
# Int = 3
# Opt = *
# Int = 21
def parser():
    print("\n\nTOKENIZER")
    express = r'(\d+)|([\:*\-+/])'
    r = re.compile(express)
    f = open("parser.txt", "r")

    for linha in f.readlines():
        print("\nLine :: ", linha)
        tokens = r.finditer(linha)
        for t in tokens:
            if t.group(1):
                print("INT: ", t.group(1))
            elif t.group(2):
                print("OP: ", t.group(2))

parser()

# TODO
# string binaria dq representam nº decimais divisiveis por 3
