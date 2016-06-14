package br.com.mindbit.controleacesso.dominio;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Evento;

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
