package com.restaurante;

public class Caixa implements Pagamento {
    @Override
    public void processarPagamento(Pedido pedido) {
        System.out.println("Pagamento do pedido " + pedido.getDetalhes() + " esta sendo processado.");
    }
}