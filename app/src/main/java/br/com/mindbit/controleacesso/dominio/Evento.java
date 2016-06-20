package br.com.mindbit.controleacesso.dominio;

import java.sql.Time;
import java.util.Date;

public class Evento {
    private int id;
    private int idPessoaCriadora;
    private String nome;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private Enum<PrioridadeEvento> nivelPrioridadeEnum;

    public Evento(){
        this.dataInicio=null;
        this.dataFim=null;
        this.nome=null;
        this.descricao=null;
        this.nivelPrioridadeEnum=null;
    }

    public int getId() {return  id;}
    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Date getDataInicio() {return dataInicio;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}

    public Date getDataFim() {return dataFim;}
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}

    public Enum<PrioridadeEvento> getNivelPrioridadeEnum() {return nivelPrioridadeEnum;}
    public void setNivelPrioridadeEnum(Enum<PrioridadeEvento> nivelPrioridadeEnum) {this.nivelPrioridadeEnum = nivelPrioridadeEnum;}


    }
