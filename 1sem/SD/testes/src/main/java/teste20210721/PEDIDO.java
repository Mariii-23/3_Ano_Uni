package teste20210721;

public enum PEDIDO {
    FORNECER,
    SER_VACINADO;

    public static PEDIDO getPedidoType(int opcode) {
        return PEDIDO.values()[opcode];
    }
}
