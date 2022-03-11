#!/usr/bin/env python3

import sys
import re

def changeDiscordToHTML():
    f = open("discord.txt", "r")
    # regex = r'(?:\*{2}((\w*|\s*)*)\*{2}|\_{2}((\w*|\s*)*)\_{2})'

    for linha in f.readlines():
        linha  = re.sub(r'\*{2}(.*)\*{2}',r'<b>\1</b>',linha)
        linha  = re.sub(r'\_{2}(.*)\_{2}',r'<i>\1</i>',linha)
        print(linha, sep='', end='')
    f.close()

changeDiscordToHTML()

def changeUsername():
    print("\nChange username\n")
    f = open("names.txt", "r")
    # regex = r'(?:\*{2}((\w*|\s*)*)\*{2}|\_{2}((\w*|\s*)*)\_{2})'

    for linha in f.readlines():
        linha  = re.sub(r'^(\s+(\w{1}).+)',r', \2',linha)
        print(linha, sep='', end='')
    f.close()

changeUsername()
