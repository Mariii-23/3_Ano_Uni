#!/usr/bin/env python3

# garantir a ordem dos cálculos
#
# 45
# 45 + 765
# 45 + 2323 * 2134 - 4234
# (45 + 2323) / (2134 - 4234)
#
#
"""
Calculo: ( OP )
       | OP

OP: OP / OP
  | OP * OP
  | Level2

Level2: Op + Op
      | Op - Op
      | Num
"""
