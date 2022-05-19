package testes;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exame18_19 {
    public class Parte1 {
        public static class ex1 {
            public interface Poly {
                void addMonomio(int grau, double coeficienet);
                double calcula(double x);
                String toString();
            }

            public static class PolyAsMap implements Poly{
                public Map<Integer,Double> poly;

                PolyAsMap() {
                    poly = new HashMap<>();
                }

                public void addMonomio(int grau, double coeficienet) {
                    double coeficiente = coeficienet;
                    if (poly.containsKey(grau) )
                        coeficiente += poly.get(grau);
                    poly.put(grau,coeficiente);
                }

                public double calcula(double x) {
                    double result = 0;
                    for(Map.Entry<Integer, Double> elem : poly.entrySet()) {
                        result += Math.pow(x, elem.getKey()) * elem.getValue();
                    }
                    return result;
                }

                public String toString() {
                    StringBuilder s = new StringBuilder();
                    for(Map.Entry<Integer, Double> elem : poly.entrySet()) {
                        if (elem.getValue() > 0)
                            s.append('+');
                        s.append(elem.getValue()).append('x').append('^').append(elem.getKey());
                    }
                    return s.toString();
                }
            }

            public static void main() {
                Poly p = new PolyAsMap();
                p.addMonomio(0,5);
                p.addMonomio(5,-10);
                p.addMonomio(2,-4);
                System.out.println(p.toString());
                System.out.println(p.calcula(1));
            }
        }
    }

    public class Parte2 {

        public abstract class Actividade implements Serializable, Comparable {
            private String designacao;
            private  double caloriasPorUnidadeTreino;
            public abstract double caloriasGastas() ;

            @Override
            public int compareTo(Object o) {
                if (o instanceof Actividade) {
                    Actividade that = (Actividade) o ;
                    return (int) (this.caloriasGastas() - that.caloriasGastas());
                }
                return -10;
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }

        private class ClienteNaoExiste extends Exception {
            public ClienteNaoExiste() {
                super();
            }

            public ClienteNaoExiste(String msg) {
                super(msg);
            }
        }

        private class Ginasio implements Serializable{
            private Map<String, Map<LocalDate, List<Actividade>>> clientes;

            public double valorTotalCalorias(String cod) throws ClienteNaoExiste {
                var cliente = this.clientes.get(cod);
                if (cliente == null) {
                    throw new ClienteNaoExiste("Cliente nao existe: " + cod);
                }
                double calorias = 0;
                for (var day : cliente.entrySet()) {
                    calorias += day.getValue().stream().mapToDouble(Actividade::caloriasGastas).sum();
                }
                return calorias;
            }

            public boolean profExiste(double cod) {
                //vamos fingir que o getCalorioas é o codigo do stor

                var iterator = clientes.entrySet().iterator();
                boolean existe = false;

                while (iterator.hasNext() && !existe) {
                    var cliente = iterator.next();

                    var lista = cliente.getValue();
                    var iter = lista.entrySet().iterator();

                    while (iter.hasNext() && !existe) {
                        var listaAtividade = iter.next().getValue();
                        existe = listaAtividade.stream().anyMatch(e->e.caloriasPorUnidadeTreino==cod);
                    }
                }
                return existe;
            }

        }

        public class parte4 {
            public class  GrowingArrayOfActividade implements  Serializable  {
                private Actividade[] lista;
                private int tamanho;

                public Actividade get(int indice) throws IndexOutOfBoundsException, CloneNotSupportedException {
                    if (tamanho < indice) {
                        throw new IndexOutOfBoundsException();
                    }
                    return (Actividade) lista[indice].clone();
                }

                public void  add(Actividade a) {
                    Actividade[] newList = new Actividade[tamanho+1];
                    System.arraycopy(lista,0,newList,0,tamanho);
                    lista = newList;
                    tamanho++;
                }

                public static GrowingArrayOfActividade ler(String file) throws IOException, ClassNotFoundException {
                    FileInputStream fos = new FileInputStream(file);
                    ObjectInputStream oos = new ObjectInputStream(fos);
                    GrowingArrayOfActividade r = (GrowingArrayOfActividade) oos.readObject();
                    oos.close();
                    fos.close();
                    return r;
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("Parte1");
        Parte1.ex1.PolyAsMap p = new Parte1.ex1.PolyAsMap();
        Parte1.ex1.main();
    }
}
