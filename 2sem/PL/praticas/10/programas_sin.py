#!/usr/bin/env python3
import ply.yacc as yacc
import math
from programas import tokens, literals

def p_Program(p):
    "Program : Declarations Statements"

def p_Declarations_list(p):
    "Declarations : Declarations Declaration"

def p_Declarations_empty(p):
    "Declarations : "

def p_Declaration(p):
    "Declaration : Type IdList"

def p_Type_int(p):
    "Type : INT"

def p_IdList_str(p):
    "IdList : IdList ',' id"

def p_IdList_single(p):
    "IdList : id"

def p_Statements_list(p):
    "Statements : Statements Statement"

def p_Statements_single(p):
    "Statements : Statement"

def p_Statement_atrib(p):
    "Statement : id '=' Exp"

def p_Statement_print(p):
    "Statement : PRINT '(' PrintArgs ')' "

def p_PrintArgs_list(p):
    "PrintArgs : PrintArgs ','  PrintArg"

def p_PrintArgs_single(p):
    "PrintArgs : PrintArg"

def p_PrintArg_id(p):
    "PrintArg : id"

def p_PrintArg_str(p):
    "PrintArg : str"

def p_PrintArg_Exp(p):
    "PrintArg : Exp"

def p_Exp_int(p):
    "Exp : INT '(' Exp ')'"

def p_Exp_input(p):
    "Exp : INPUT '(' str ')'"

def p_error(p):
    print("Erro sintatico: ",p)
    parser.success = False

# Build the parser
parser = yacc.yacc()

# Read line from input and parse it
import sys
parser.success = True
program = sys.stdin.read()
parser.parse(program)
if parser.success:
    print("Sucesso")
else:
    print("Erro")
