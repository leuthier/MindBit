package br.com.mindbit.dominio;

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

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }

}



