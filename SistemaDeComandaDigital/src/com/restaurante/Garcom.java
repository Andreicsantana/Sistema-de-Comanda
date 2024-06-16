package com.restaurante;

public class Garcom extends Pessoa {
    public Garcom(String nome, String contato) {
        super(nome, contato);
    }

    @Override
    public void realizarAcao() {
        System.out.println("Garcom esta anotando o pedido.");
    }

    public Pedido anotarPedido(Cliente cliente, String detalhes) {
        return new Pedido(cliente, detalhes);
    }
}
