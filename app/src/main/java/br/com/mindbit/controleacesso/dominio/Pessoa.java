package br.com.mindbit.controleacesso.dominio;
import android.net.Uri;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Usuario;

public class Pessoa {
    private int id;
    private Usuario usuario;
    private String nome;
    private String email;
    private Uri foto;
    private List<Amigo> amigos;

    public Pessoa() {
        this.nome = null;
        this.email = null;
        this.foto = null;
        this.usuario=null;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){ this.id = id; }

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public String getNome(){return this.nome;}
    public void setNome(String nome){ this.nome = nome; }

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

    public List<Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    public void addAmigo(Amigo amigo){
        amigos.add(amigo);
    }

}


