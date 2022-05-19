package testes;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class Teste2021 {
    // Parte 1
    // 1. c
    // 2. b e d)
    // 3. d
    // 4. b
    // 5. d

    public static class Parte2 {
        public class SmartDevice implements Comparable<SmartDevice> {
            private String id;
            private boolean on;
            private double consumoPorHora;
            private LocalDateTime inicio;

            public SmartDevice( String id, double consumoPorHora) {
                this.id = id;
                this.on = false;
                this.consumoPorHora = consumoPorHora;
            }

            public SmartDevice(SmartDevice that) {
                this.id = that.id;
                this.on = that.on;
                this.consumoPorHora = that.consumoPorHora;
                this.inicio = that.inicio;
            }

            // devolve o consumo desde o inicio
            public double totalConsumo() {return 0;}
            //liga o device. Se for a primeira vez inicializa o tempo de inicio
            public void turnOn() {
                this.on = true;
                if (this.inicio == null)
                    this.inicio = LocalDateTime.now();
            }

            public String getId() {
                return id;
            }

            public SmartDevice clone() {
                return new SmartDevice(this);
            }
            @Override
            public int compareTo(SmartDevice o) {
                return (int) (this.totalConsumo() - o.totalConsumo());
            }
        }

        public class SmartBulb extends SmartDevice implements Comparable<SmartDevice> {
            public static final int WARM = 2;
            public static final int NEUTRAL = 1;
            public static final int COLD = 0;
            private int tone;
            public SmartBulb(String id, int tone, double consumoPorHora) {
                super(id, consumoPorHora);
                this.tone = tone;
            }

            public void setTone(int t) {
                if (t>WARM) this.tone = WARM;
                else if (t<COLD) this.tone = COLD;
                else this.tone = t;
            }
            public int getTone() {
                return this.tone;
            }

            @Override
            public int compareTo(SmartDevice o) {
                return (int) (this.totalConsumo() - o.totalConsumo());
            }
        }
        public class SmartSpeaker extends SmartDevice implements Comparable<SmartDevice>, Serializable {
            public static final int MAX = 20; //volume maximo da coluna
            private int volume;
            private String channel;
            public SmartSpeaker(String id, String channel, double consumoPorHora) {
                super(id, consumoPorHora);
                this.channel = channel;
                this.volume = 10;
            }

            @Override
            public int compareTo(SmartDevice o) {
                return (int) (this.totalConsumo() - o.totalConsumo());
            }
        }

        //6.
        public class CasaInteligente {
            private Map<String, SmartDevice> devices; // identificador -> SmartDevice
            private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

            public CasaInteligente(Collection<SmartDevice> devices) {
                this.devices = new HashMap<>();
                this.locations = new HashMap<>();
                devices.stream().map(SmartDevice::clone).forEach(e-> this.devices.put(e.getId(), e));
            }

            //8. a excecao foi feita fora
            public void  remove(String id) throws SmartDeviceNotFoundException {
                SmartDevice device = this.devices.remove(id);
                if (device == null)
                    throw new SmartDeviceNotFoundException();
                this.locations.values().forEach(e-> e.remove(id));
            }

            //9
            // para ser de ordem natural temos q alterar as classes
            // tem q implementar o Comparable
            public Iterator<SmartDevice> devicesPorConsumoCrescente(){
                return this.devices.values().stream().sorted().iterator();
            }

            //10
            public String divisaoMaisEconomica() {
                Comparator<Pair<String,Double>> comparator = (e1,e2) -> {
                    if (Objects.equals(e1.getSecond(), e2.getSecond()))
                        return e1.getFirst().length() - e2.getFirst().length();
                    return (int) (e1.getSecond() - e2.getSecond());
                };

                var maximo =  this.locations.entrySet()
                        .stream()
                        .map(e->
                            new Pair<>(e.getKey(),e.getValue().stream().mapToDouble(value-> this.devices.get(value).totalConsumo()).sum())
                        )
                        .max(comparator);
                //vamos assumir q ha sempre uma solucao
                return maximo.get().getFirst();
            }

            // 12
            Consumer<SmartBulbDimmable>  smartBulbDimmableConsumer = e -> e.setIntensidade(e.getIntensidade()*0.25);
            public void alteraInfo(Consumer<SmartBulbDimmable> bd) {
                this.devices
                        .values()
                        .stream()
                        .filter(e -> e instanceof SmartBulbDimmable)
                        .map(e-> (SmartBulbDimmable) e)
                        .forEach(bd::accept);
            }

            //13
            public boolean apenasNumaDivisao() {
               for( String device: this.devices.keySet())  {
                   boolean find = false;
                   for(List<String> list : this.locations.values()) {
                       if (find) {
                            if (list.contains(device))
                                return false;
                       } else
                           find = list.contains(device);
                   }
               }
               return true;
            }

            //14
            public boolean gravaEmFicheiroObjectos(String fich) throws IOException {
                List<SmartSpeaker> list = this.devices
                        .values()
                        .stream()
                        .filter(e -> e instanceof SmartSpeaker)
                        .map(e-> (SmartSpeaker) e)
                        .toList();

                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fich));
                os.writeObject(list);
                os.flush();
                os.close();
                return true;
            }
        }

        //11
        public class SmartBulbDimmable extends SmartBulb {
            private double intensidade;

            public SmartBulbDimmable(String id, double consumoPorHora, int tone) {
                super(id,tone, consumoPorHora);
                intensidade = 0.5;
            }

            public double getIntensidade() {
                return intensidade;
            }

            public void setIntensidade(double intensidade) {
                this.intensidade = intensidade;
            }

            @Override
            public double totalConsumo() {
                return super.totalConsumo() * intensidade;
            }
        }

        public class Pair<T,U> {
            private T first;
            private U second;

            public Pair(T first, U second) {
                this.first = first;
                this.second = second;
            }

            public T getFirst() {
                return first;
            }

            public U getSecond() {
                return second;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Pair<?, ?> pair = (Pair<?, ?>) o;
                return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
            }

            @Override
            public int hashCode() {
                return Objects.hash(first, second);
            }
        }

        //8.
        public class SmartDeviceNotFoundException extends Exception {
            public SmartDeviceNotFoundException() {
            }
        }
    }
}
