package com.restaurante;

public class Cliente extends Pessoa {
    public Cliente(String nome, String contato) {
        super(nome, contato);
    }

    @Override
    public void realizarAcao() {
        System.out.println("Cliente esta fazendo um pedido.");
    }
}
