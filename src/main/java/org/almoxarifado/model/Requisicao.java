package org.almoxarifado.model;

import java.time.LocalDate;

public class Requisicao {
    private int id;
    private String setor;
    private LocalDate dataSolicitacao;

    public Requisicao(int id, String setor, LocalDate dataSolicitacao) {
        this.id = id;
        this.setor = setor;
        this.dataSolicitacao = dataSolicitacao;
    }

    public Requisicao(String setor, LocalDate dataSolicitacao) {
        this.setor = setor;
        this.dataSolicitacao = dataSolicitacao;
    }

    @Override
    public String toString() {
        return "\nREQUISIÇÃO:" +
                "\nID: " + id +
                "\nSetor: " + setor +
                "\nData da solicitacao: " + dataSolicitacao;
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

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}
