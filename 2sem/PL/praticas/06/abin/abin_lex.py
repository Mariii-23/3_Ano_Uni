#
#
# ABin -> ( Abin2 )
#
# ABin2 -> E
#       | Num ABin ABin
#

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
