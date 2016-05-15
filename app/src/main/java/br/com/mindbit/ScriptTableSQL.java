package br.com.mindbit;

/**
 * Created by Bruna on 14/05/2016.
 */
public class ScriptTableSQL {
    public static String getTabelaPessoa() {

        StringBuilder sqlBilder = new StringBuilder();
        sqlBilder.append("CREATE TABLE  tabela_pessoa  (  ");
        sqlBilder.append("_id_pessoa   integer primary key autoincrement,   ");
        sqlBilder.append("nome_pessoa  text not null,  ");
        sqlBilder.append("cpf_pessoa   text not null unique,   ");
        sqlBilder.append("data_nascimento_pessoa date,");
        sqlBilder.append("foto_pessoa text not null);");
        return sqlBilder.toString();

    }

    public static String getTabelaUsuario() {

        StringBuilder userBild = new StringBuilder();
        userBild.append("CREATE TABLE  tabela_usuario ( ");
        userBild.append("_id_usuario   integer primary key autoincrement,   ");
        userBild.append("login_usuario  text not null unique,  ");
        userBild.append("senha_usuario  text not null, ");
        userBild.append("id_pessoa_usuario   integer,  ");
        userBild.append("foreign key ( id_pessoa_usuario ) references  tabela_pessoa ( _id_pessoa ) );");
        return userBild.toString();

    }

}