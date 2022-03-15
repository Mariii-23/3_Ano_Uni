package ficha03;

import java.time.LocalDate;
import java.util.Arrays;

public class Encomenda {
    String cliente;
    String fiscal;
    String morada;
    LocalDate data;
    LinhaDeEncomenda[] encomendas;

    public Encomenda(String cliente, String fiscal, String morada, LocalDate data, LinhaDeEncomenda[] encomendas) {
        this.cliente = cliente;
        this.fiscal = fiscal;
        this.morada = morada;
        this.data = data;
        this.encomendas = encomendas;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LinhaDeEncomenda[] getEncomendas() {
        var r = new LinhaDeEncomenda[encomendas.length];
        for (int i = 0; i < encomendas.length; i++) {
            r[i] = encomendas[i].clone();
        }
        return r;
    }

    public void setEncomendas(LinhaDeEncomenda[] encomendas) {
        this.encomendas = new LinhaDeEncomenda[encomendas.length];
        for (int i = 0; i < encomendas.length; i++) {
            this.encomendas[i] = encomendas[i].clone();
        }
    }

    public double calculaValorTotal() {
        return Arrays.stream(encomendas).mapToDouble(LinhaDeEncomenda::calculaValorLinhaEncomenda).sum();
    }

    public double calculaValorDesconto() {
        return Arrays.stream(encomendas).mapToDouble(LinhaDeEncomenda::calculaValorDesconto).sum();
    }

    public int numeroTotalProdutos() {
        return Arrays.stream(encomendas).mapToInt(LinhaDeEncomenda::getQuantidade).sum();
    }

    public boolean existeProdutoEncomenda(String refProduto) {
        return Arrays.stream(encomendas).anyMatch(e -> e.getCodigo().equals(refProduto));
    }

    public void addProdutoEncomenda(LinhaDeEncomenda linhaDeEncomenda) {
        var aux = new LinhaDeEncomenda[1 + encomendas.length];
        int i;
        for (i = 0; i < encomendas.length; i++)
            aux[i] = encomendas[i];
        aux[i] = linhaDeEncomenda.clone();
        encomendas = aux;
    }

    public void removeProduto(String codProd) {
        if (existeProdutoEncomenda(codProd)) {
            boolean encontrei = false;
            for (int i = 0; i < encomendas.length; i++) {
                if (encontrei)
                    encomendas[i - 1] = encomendas[i];
                else
                    encontrei = encomendas[i].getCodigo().equals(codProd);
            }
            encomendas = Arrays.copyOf(encomendas, encomendas.length - 1);
        }
    }
}
