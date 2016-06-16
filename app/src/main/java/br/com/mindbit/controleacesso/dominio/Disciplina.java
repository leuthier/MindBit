package br.com.mindbit.controleacesso.dominio;


public class Disciplina {
    private String codigo;
    private String nome;
    private int id;

    public Disciplina(){
        this.codigo = null;
        this.nome = null;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getCodigo(){return codigo;}

    public void setCodigo(String codigo){this.codigo = codigo;}

    public String getNome(){return nome;}

    public void setNome(String nome){this.nome = nome;}
}
