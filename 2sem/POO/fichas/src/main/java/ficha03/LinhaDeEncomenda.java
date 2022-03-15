package ficha03;

public class LinhaDeEncomenda {
    private String codigo;
    private String descricao;
    private float preco;
    private int quantidade;
    private float imposto;
    private float desconto;

    public LinhaDeEncomenda(LinhaDeEncomenda that) {
        this.codigo =    that.codigo;
        this.descricao = that.descricao;
        this.preco =     that.preco;
        this.quantidade =that.quantidade;
        this.imposto =   that.imposto;
        this.desconto =  that.desconto;
    }

    public LinhaDeEncomenda(String codigo, String descricao, float preco, int quantidade, float imposto, float desconto) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imposto = imposto;
        this.desconto = desconto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getImposto() {
        return imposto;
    }

    public void setImposto(float imposto) {
        this.imposto = imposto;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public double calculaValorLinhaEncomenda(){
        return preco * (1 + imposto/100) - calculaValorDesconto();
    }

    public double calculaValorDesconto() {
        return desconto/100 * preco;
    }

    protected LinhaDeEncomenda clone() {
        return new LinhaDeEncomenda(this);
    }
}
