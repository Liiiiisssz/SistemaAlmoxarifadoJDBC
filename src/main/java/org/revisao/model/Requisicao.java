package org.revisao.model;

import java.time.LocalDate;

public class Requisicao {
    private int id;
    private String setor;
    private LocalDate dataSoliciacao;
    private String status;

    public Requisicao(){}

    public Requisicao(int id, String setor, LocalDate dataSoliciacao, String status) {
        this.id = id;
        this.setor = setor;
        this.dataSoliciacao = dataSoliciacao;
        this.status = status;
    }

    public Requisicao(String setor, LocalDate dataSoliciacao, String status) {
        this.setor = setor;
        this.dataSoliciacao = dataSoliciacao;
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n\nRequisicao:" +
                "\nID: " + id +
                "\nSetor: " + setor +
                "\nData de soliciação: " + dataSoliciacao +
                "\nStatus: " + status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public LocalDate getDataSoliciacao() {
        return dataSoliciacao;
    }

    public void setDataSoliciacao(LocalDate dataSoliciacao) {
        this.dataSoliciacao = dataSoliciacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
