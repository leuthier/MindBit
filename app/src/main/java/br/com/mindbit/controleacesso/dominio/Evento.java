package br.com.mindbit.controleacesso.dominio;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Ariana on 06/06/2016.
 */
public class Evento {
    private int id;
    private int idPessoaCriadora;
    private String nome;
    private String descricao;
    private Time horaInicio;
    private Time horaFim;
    private Date dataInicio;
    private Date dataFim;
    private Enum<PrioridadeEvento> nivelPrioridadeEnum;

    public Evento(){
        this.nome=null;
        this.descricao=null;
        this.horaInicio=null;
        this.horaFim=null;
        this.dataInicio=null;
        this.dataFim=null;
        this.nivelPrioridadeEnum=null;
    }

    public int getId() {return  id;}
    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Time getHoraInicio() {return horaInicio;}
    public void setHoraInicio(Time horaInicio) {this.horaInicio = horaInicio;}

    public Time getHoraFim() {return horaFim;}
    public void setHoraFim(Time horaFim) {this.horaFim = horaFim;}

    public Date getDataInicio() {return dataInicio;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}

    public Date getDataFim() {return dataFim;}
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}

    public Enum<PrioridadeEvento> getNivelPrioridadeEnum() {return nivelPrioridadeEnum;}
    public void setNivelPrioridadeEnum(Enum<PrioridadeEvento> nivelPrioridadeEnum) {this.nivelPrioridadeEnum = nivelPrioridadeEnum;}

    public int getIdPessoaCriadora() {return  idPessoaCriadora;}
    public void setIdPessoaCriadora(int idPessoaCriadora) {this.idPessoaCriadora = idPessoaCriadora;}

    public void compartilhar(){
        }
}
