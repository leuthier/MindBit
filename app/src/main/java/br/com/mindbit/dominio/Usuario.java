package br.com.mindbit.dominio;

/**
 * Created by Bruna on 14/05/2016.
 */

public class Usuario {

    private String login;
    private int id;
    private String senha;


    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario(){

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getLogin() {return login;}
    public int getId() {
        return id;
    }
    public String getSenha() {
        return senha;
    }
}



