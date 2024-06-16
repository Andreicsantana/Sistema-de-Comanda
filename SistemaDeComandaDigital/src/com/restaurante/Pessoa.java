package com.restaurante;

public abstract class Pessoa {
    protected String nome;
    protected String contato;

    public Pessoa(String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }

    public abstract void realizarAcao();
}