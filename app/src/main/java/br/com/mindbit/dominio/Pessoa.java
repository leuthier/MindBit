package br.com.mindbit.dominio;
import android.net.Uri;

/**
 * Created by Bruna on 14/05/2016.
 */
public class Pessoa {
    private Usuario usuario;
    private String nome, cpf, email;
    private Uri foto;

    public Pessoa(Usuario usuario, String nome, String cpf, String email, Uri foto) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.foto = foto;
    }

    public Pessoa(){

    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setFoto(Uri foto) {
        this.foto = foto;
    }
    public String getNome(){return this.nome;}
    public String getCpf() {return cpf;}
    public String getEmail(){return this.email;}
    public Uri getFoto() {return foto;}

}


