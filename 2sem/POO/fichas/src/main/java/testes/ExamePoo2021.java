package testes;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ExamePoo2021 {
    // Parte 1
    // 1. d
    // 2. c
    // 3.
    //     a)  2 e 3?
    //     b)  1
    //     c)  3
    //     d)  ?
    // 4. b
    // 5. c

    // Parte 2
    public class Parte2 {
        // vamos considerar q é tudo privado e tem os gets apropriados

        public class Episodio implements Playable  {
            String nome;
            double duracao;
            int classificacao;
            List<String> conteudo;
            int numeroVezesTocada;
            LocalDateTime ultimavez;

            public Episodio(String nome, double duracao, int classificacao, List<String> conteudo, int numeroVezesTocada, LocalDateTime ultimavez) {
                this.nome = nome;
                this.duracao = duracao;
                this.classificacao = classificacao;
                this.conteudo = conteudo;
                this.numeroVezesTocada = numeroVezesTocada;
                this.ultimavez = ultimavez;
            }

            protected Episodio clone() {
                return new Episodio(this.nome, this.duracao, this.classificacao, this.conteudo, this.numeroVezesTocada, this.ultimavez);
            }

            @Override
            public void play() {
                for( String frase : conteudo)
                    // vamos fingir que é o metodo System.media()
                    System.out.println(frase);
            }
        }

        public class Podcast {
            String nome;
            Map<String, Episodio> episodios; // todos os episodios do podcast
        }

        public class Utilizador implements Comparable<Utilizador> {
            String mail;  // id
            String nome;
            Map<String, Podcast> podcasts; // todos os podcasts subscritos

            public Utilizador(String mail, String nome, Map<String, Podcast> podcasts) {
                this.mail = mail;
                this.nome = nome;
                this.podcasts = podcasts;
            }

            public void playEpisodio (String idPodCast, String nomeEpisodio) throws AlreadyPlayingException {

            }

            @Override
            public int compareTo(Utilizador o) {
                return 0;
            }
        }

        public class podcastNotFound extends Exception {
            public podcastNotFound() {}
        }

        public class existemSubscricoes extends Exception {
            public existemSubscricoes() {}
        }
        public class AlreadyPlayingException extends Exception {
            public AlreadyPlayingException() {}
        }

        public class SpotifyPoo {
            Map<String, Podcast> podcasts;
            Map<String, Utilizador> utilizadores;

            public List<Episodio> getEpisodios(String nomePodcast) throws podcastNotFound {
                if (!this.podcasts.containsKey(nomePodcast))
                    throw new podcastNotFound();

                return this.podcasts.get(nomePodcast).episodios.values()
                        .stream().map(Episodio::clone).collect(Collectors.toList());
            }

            // 8.
            public void remover(String nomeP) throws podcastNotFound, existemSubscricoes {
                if (!this.podcasts.containsKey(nomeP))
                    throw new podcastNotFound();

                boolean alguemASubscrever = this.utilizadores.values().stream()
                        .anyMatch(e-> e.podcasts.containsKey(nomeP));

                if (alguemASubscrever)
                    throw new existemSubscricoes();

                this.podcasts.remove(nomeP);
            }

            // 9.
            //vamos considerar q o utilizador existe
            public Episodio getEpisodioMaisLongo(String u) {
                Utilizador utilizador = this.utilizadores.get(u);

                HashSet<Episodio> episodios = new HashSet<>();

                utilizador.podcasts.values().stream()
                        .map(e-> e.episodios.values().stream().
                                map(elem -> episodios.add(elem.clone())));

                Comparator<Episodio> c = (e1, e2) -> (int) (e1.duracao - e2.duracao);

                // falta so verificar se existem episodios
                // no caso do set ser vazio retorna se excecao a dizer q nao ha episodios
                if(episodios.isEmpty())
                    return null;

                return episodios.stream().sorted(c).iterator().next();
            }

            // 10.
            public Map<Integer, List<Episodio>> episodisoPorClassf() {
                //TODO fazer com o groupingBy
                //this.podcasts.values().stream().map(
                //        e-> e.episodios
                //).collect(Collectors.toSet()).stream().collect(
                //        groupingBy()
                //)
                HashMap<Integer, List<Episodio>> map = new HashMap<>();
                for(Podcast podcast : this.podcasts.values()) {
                    for(Episodio ep : podcast.episodios.values()) {
                        List<Episodio> list;
                        if (map.containsKey(ep.classificacao)) {
                           list = map.get(ep.classificacao);
                           list.add(ep.clone());
                        } else {
                            list = new ArrayList<>();
                            list.add(ep.clone());
                        }
                        map.put(ep.classificacao, list);
                    }
                }
                return map;
            }

            public void gravaInfoEpisodiosParaTocarMaisTarde(String fich) throws IOException {
                FileWriter file = new FileWriter(fich);
                List<UtilizadorPremium> u = this.utilizadores
                        .values()
                        .stream()
                        .filter(e -> e instanceof UtilizadorPremium)
                        .map(e -> (UtilizadorPremium) e)
                        .toList();
                for(UtilizadorPremium elem : u) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(elem.nome).append("\n");

                    for(Pair<String,String> ep : elem.listaAReproduzir) {
                        sb.append(ep.first).append(" - ");
                        // FIXME isto ta muito macumbado
                        try {
                            sb.append(getEpisodios(ep.first).stream().filter(e-> Objects.equals(e.nome, ep.second)).findFirst().get().duracao);
                        } catch (podcastNotFound e) {
                            e.printStackTrace();
                        }
                        sb.append("\n");
                    }
                    file.write(sb.toString());
                }
                file.flush();
                file.close();
            }
        }

        // 11.
        public interface Playable {
            public void play();
        }
        // acrescentado a classe episodio

        // 12.
        public class EpisodioVideo extends Episodio {
            List<Byte> video;

            public EpisodioVideo(String nome, double duracao, int classificacao, List<String> conteudo, int numeroVezesTocada, LocalDateTime ultimavez, List<Byte> video) {
                super(nome, duracao, classificacao, conteudo, numeroVezesTocada, ultimavez);
                this.video = video;
            }

            @Override
            public void play() {
                // fingir q é o System.media
                for (Byte pedaco : video)
                    System.out.println(pedaco);

                super.play();
            }
        }

        public class Pair<T,U> {
            T first;
            U second;

            public Pair(T first, U second) {
                this.first = first;
                this.second = second;
            }
        }

        // 13
        public class UtilizadorPremium extends Utilizador {
            Queue<Pair<String,String>> listaAReproduzir = new LinkedList<>();

            public UtilizadorPremium(String mail, String nome, Map<String, Podcast> podcasts) {
                super(mail, nome, podcasts);
            }

            //TODO verificar
            @Override
            public void playEpisodio(String idPodCast, String nomeEpisodio) throws AlreadyPlayingException {
                try {
                    super.playEpisodio(idPodCast, nomeEpisodio);
                } catch (AlreadyPlayingException e) {
                    listaAReproduzir.add(new Pair<>(idPodCast, nomeEpisodio));
                    throw new AlreadyPlayingException();
                }
            }
        }

        // 14.
        // no metodo spotify
    }
}
