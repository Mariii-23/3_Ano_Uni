%% faltam coisas


expande_agulosa_tempo_g(Caminho,ExpCaminhos):-
    findall(NovoCaminho,adjacente_tempo(Caminho,NovoCaminho),ExpCaminhos).

adjacente_distancia([Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/EstDist) :-
    move(Nodo,ProxNodo,PassoCustoDist,_),
    \+ member(ProxNodo,Caminho),
    NovoCusto is Custo + PassoCustoDist,
    estima(ProxNodo,EstDist,_).

adjacente_tempo([Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/EstimaTempo) :-
    move(Nodo,ProxNodo,_,PassoTempo),
    \+ member(ProxNodo,Caminho),
    NovoCusto is Custo + PassoTempo,
    estima(ProxNodo,_,EstimaTempo).
