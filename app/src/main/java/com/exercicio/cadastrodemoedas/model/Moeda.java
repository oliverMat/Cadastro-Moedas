package com.exercicio.cadastrodemoedas.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "DB_MOEDAS")
public class Moeda implements Serializable {

    @PrimaryKey
    private int codigo;

    @NonNull
    private String descricao;

    private String abreviatura;
    private Float cotacao;

    public Moeda(int codigo, @NonNull String descricao, String abreviatura, Float cotacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.abreviatura = abreviatura;
        this.cotacao = cotacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Float getCotacao() {
        return cotacao;
    }

    public void setCotacao(Float cotacao) {
        this.cotacao = cotacao;
    }
}
