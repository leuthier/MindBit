package br.com.mindbit.controleacesso.persistencia;

import android.database.sqlite.SQLiteDatabase;

import br.com.mindbit.infra.gui.GuiUtil;


public class ScriptPopularTableSQL {
    public static void inserirPessoas(SQLiteDatabase db) {

        String[] pessoas = {"INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,cpf_pessoa,email_pessoa,foto_pessoa) VALUES (1,'bruna','12348652197','bruna@hotmail.com','"+ GuiUtil.IMAGEM_PADRAO+"');",
                "INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,cpf_pessoa,email_pessoa,foto_pessoa) VALUES (2,'victor','15976532415','victor@ufrpe.br','"+GuiUtil.IMAGEM_PADRAO+"');"};

        for (String pessoa : pessoas) {
            db.execSQL(pessoa);
        }
    }
    public static void inserirUsuarios(SQLiteDatabase db) {

        String[] usuarios = {"INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (1,'bruna','bruna10',1);",
                "INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (2,'victor','victor10',2);"};

        for (String usuario : usuarios) {
            db.execSQL(usuario);
        }
    }


}

