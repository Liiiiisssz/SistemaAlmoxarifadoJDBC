package org.almoxarifado.model;

import java.time.LocalDate;

public class NotaEntrada {
    private int id;
    private Fornecedor fornecedor;
    private LocalDate dataEntrega;

    public NotaEntrada(int id, Fornecedor fornecedor, LocalDate dataEntrega) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.dataEntrega = dataEntrega;
    }

    public NotaEntrada(Fornecedor fornecedor, LocalDate dataEntrega) {
        this.fornecedor = fornecedor;
        this.dataEntrega = dataEntrega;
    }

    @Override
    public String toString() {
        return "\nNOTA DE ENTRADA:" +
                "\nID: " + id +
                "\nFornecedor: " + fornecedor +
                "\nData de entrega: " + dataEntrega;
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

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
