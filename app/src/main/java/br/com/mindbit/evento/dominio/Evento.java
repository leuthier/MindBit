package br.com.mindbit.evento.dominio;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Ariana on 06/06/2016.
 */
public class Evento {
    private String nome;
    private StringBuilder descricao;
    private Time horaInicio;
    private Time horaFim;
    private Date dataInicio;
    private Date dataFim;
    private Enum<NivelPrioridade> nivelPrioridadeEnum;

    public Evento(){
        this.nome=null;
        this.descricao=null;
        this.horaInicio=null;
        this.horaFim=null;
        this.dataInicio=null;
        this.dataFim=null;
        this.nivelPrioridadeEnum=null;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public StringBuilder getDescricao() {return descricao;}
    public void setDescricao(StringBuilder descricao) {this.descricao = descricao;}

    public Time getHoraInicio() {return horaInicio;}
    public void setHoraInicio(Time horaInicio) {this.horaInicio = horaInicio;}

    public Time getHoraFim() {return horaFim;}
    public void setHoraFim(Time horaFim) {this.horaFim = horaFim;}

    public Date getDataInicio() {return dataInicio;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}

    public Date getDataFim() {return dataFim;}
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}

    public Enum<NivelPrioridade> getNivelPrioridadeEnum() {return nivelPrioridadeEnum;}
    public void setNivelPrioridadeEnum(Enum<NivelPrioridade> nivelPrioridadeEnum) {this.nivelPrioridadeEnum = nivelPrioridadeEnum;}

    public void compartilhar(){
        }
}