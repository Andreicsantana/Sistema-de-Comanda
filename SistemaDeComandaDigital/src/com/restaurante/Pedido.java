package com.restaurante;

public class Pedido {
    private Cliente cliente;
    private String detalhes;

    public Pedido(Cliente cliente, String detalhes) {
        this.cliente = cliente;
        this.detalhes = detalhes;
    }

    public String getDetalhes() {
        return detalhes;
    }
}
