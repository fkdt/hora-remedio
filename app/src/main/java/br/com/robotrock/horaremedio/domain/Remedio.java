package br.com.robotrock.horaremedio.domain;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class Remedio {
    private String nome;
    private String tipo_dosagem;
    private String tempo_tratamento;
    private int dose;
    private int intervalo;
    private int quantidade;

    public Remedio(String nome, String tipo_dosagem, String tempo_tratamento,
                   int dose, int intervalo, int quantidade){
        this.nome = nome;
        this.tipo_dosagem = tipo_dosagem;
        this.tempo_tratamento = tempo_tratamento;
        this.dose = dose;
        this.intervalo = intervalo;
        this.quantidade = quantidade;
    }

    public Remedio(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_dosagem() {
        return tipo_dosagem;
    }

    public void setTipo_dosagem(String tipo_dosagem) {
        this.tipo_dosagem = tipo_dosagem;
    }

    public String getTempo_tratamento() {
        return tempo_tratamento;
    }

    public void setTempo_tratamento(String tempo_tratamento) {
        this.tempo_tratamento = tempo_tratamento;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }



}
