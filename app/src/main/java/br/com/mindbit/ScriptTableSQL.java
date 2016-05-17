package br.com.mindbit;

/**
 * Created by Bruna on 14/05/2016.
 */
public class ScriptTableSQL {
    public static String getTabelaPessoa() {

        StringBuilder pessoaBuilder = new StringBuilder();
        pessoaBuilder.append("CREATE TABLE  tabela_pessoa  (  ");
        pessoaBuilder.append("_id_pessoa   integer primary key autoincrement,   ");
        pessoaBuilder.append("nome_pessoa  text not null,  ");
        pessoaBuilder.append("cpf_pessoa   text not null unique,   ");
        pessoaBuilder.append("email_pessoa text not null unique,   ");
        pessoaBuilder.append("foto_pessoa text not null);");
        return pessoaBuilder.toString();
    }

    public static String getTabelaUsuario() {

        StringBuilder usuarioBuilder = new StringBuilder();
        usuarioBuilder.append("CREATE TABLE  tabela_usuario ( ");
        usuarioBuilder.append("_id_usuario   integer primary key autoincrement,   ");
        usuarioBuilder.append("login_usuario  text not null unique,  ");
        usuarioBuilder.append("senha_usuario  text not null, ");
        usuarioBuilder.append("id_pessoa_usuario   integer,  ");
        usuarioBuilder.append("foreign key ( id_pessoa_usuario ) references  tabela_pessoa ( _id_pessoa ) );");
        return usuarioBuilder.toString();
    }

    public static String getTabelaEvento(){

        StringBuilder eventoBuilder = new StringBuilder();
        eventoBuilder.append("CREATE TABLE  tabela_evento ( ");
        eventoBuilder.append("_id_evento   integer primary key autoincrement,   ");
        eventoBuilder.append("login_usuario  text not null unique,  ");
        eventoBuilder.append("senha_usuario  text not null, ");
        eventoBuilder.append("id_pessoa_usuario   integer,  ");
        eventoBuilder.append("foreign key ( id_pessoa_usuario ) references  tabela_pessoa ( _id_pessoa ) );");
        return eventoBuilder.toString();
    }

}