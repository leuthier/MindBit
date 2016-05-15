package br.com.mindbit;

import java.util.List;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bruna on 14/05/2016.
 */
public class ScriptPopularTableSQL {
    public static void inserirPessoasDB(SQLiteDatabase db) {

        String[] pessoas = {"INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,cpf_pessoa) VALUES (1,'bruna','10333744403');"};

        for (String pessoa : pessoas) {
            db.execSQL(pessoa);
        }
    }
    public static void inserirUsuariosDB(SQLiteDatabase db) {

        String[] usuarios = {"INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (1,'bruna','bruna',1);"};

        for (String usuario : usuarios) {
            db.execSQL(usuario);
        }
    }
}

