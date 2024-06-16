package com.restaurante;

public class Cozinheiro extends Pessoa {
    public Cozinheiro(String nome, String contato) {
        super(nome, contato);
    }

    @Override
    public void realizarAcao() {
        System.out.println("Cozinheiro esta preparando o pedido.");
    }

    public void prepararPedido(Pedido pedido) {
        System.out.println("Pedido " + pedido.getDetalhes() + " esta sendo preparado.");
    }
}
