package br.com.mindbit.aluno.dominio;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.evento.dominio.Evento;

/**
 * Created by Ariana on 06/06/2016.
 */
public class Aluno {
    private int anoEntrada;
    private Pessoa pessoa;
    private List<Evento> evento;

    public Aluno(){
        this.anoEntrada=0;
        this.pessoa=null;
        this.evento=null;
    }

    public List<Evento> getEvento() {return evento;}
    public void setEvento(List<Evento> evento) {this.evento = evento;}

    public Pessoa getPessoa() {return pessoa;}
    public void setPessoa(Pessoa pessoa) {this.pessoa = pessoa;}

    public int getAnoEntrada() {return anoEntrada;}
    public void setAnoEntrada(int anoEntrada) {this.anoEntrada = anoEntrada;}


}
