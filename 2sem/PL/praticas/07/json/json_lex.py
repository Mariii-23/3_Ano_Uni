#!/usr/bin/env python3
# (22 (13 () ()) (43 () ()))
#
# Converte para:
#
# {
#     "root": 22,
#     "left" : {
#         "root": 13,
#         "left": null,
#         "right": null,
#     },
#     "right" : {
#         "root": 43,
#         "left": null,
#         "right": null,
#     }
# }

import ply.lex as lex

tokens = ['PA', 'PF', 'NUM']

t_PA = r'\('
t_PF = r'\)'
t_NUM = r'\d+'

t_ANY_ignore = "\t\n "

def t_error(t):
    print(f"ERROR: Illegal character '{t.value[0]}' at position ({t.lineno}, {t.lexpos})")
    t.lexer.skip(1)

lexer = lex.lex()
