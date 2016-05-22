package br.com.mindbit.controleacesso.dominio;
import android.net.Uri;

import br.com.mindbit.controleacesso.dominio.Usuario;

public class Pessoa {
    private int id;
    private Usuario usuario;
    private String nome;
    private String cpf;
    private String email;
    private Uri foto;

    public Pessoa() {
        this.nome = null;
        this.cpf = null;
        this.email = null;
        this.foto = null;
        this.usuario=null;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Uri getFoto() {
        return foto;
    }
    public void setFoto(Uri foto) {
        this.foto = foto;
    }

}


