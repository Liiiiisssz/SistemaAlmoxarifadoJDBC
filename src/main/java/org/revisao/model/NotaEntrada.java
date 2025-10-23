package org.revisao.model;

import java.time.LocalDate;

public class NotaEntrada {
    private int id;
    private Fornecedor fornecedor;
    private LocalDate dataEntrada;

    public NotaEntrada(int id, Fornecedor fornecedor, LocalDate dataEntrada) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.dataEntrada = dataEntrada;
    }

    public NotaEntrada(Fornecedor fornecedor, LocalDate dataEntrada) {
        this.fornecedor = fornecedor;
        this.dataEntrada = dataEntrada;
    }

    @Override
    public String toString() {
        return "\n\nNota de Entrada:" +
                "\nID: " + id +
                "\nFornecedor: " + fornecedor +
                "\nData de entrada: " + dataEntrada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
