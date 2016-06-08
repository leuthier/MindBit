package br.com.mindbit.controleacesso.persistencia;

import android.database.sqlite.SQLiteDatabase;

import br.com.mindbit.infra.gui.GuiUtil;


public class PopularTabela {
    public static void inserirPessoas(SQLiteDatabase db) {

        String[] pessoas =
                {"INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,email_pessoa,foto_pessoa) VALUES (1,'bruna','bruna@hotmail.com','"+ GuiUtil.IMAGEM_PADRAO+"');",
                "INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,email_pessoa,foto_pessoa) VALUES (2,'victor','victor@ufrpe.br','"+GuiUtil.IMAGEM_PADRAO+"');",
                "INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,email_pessoa,foto_pessoa) VALUES (3,'tiago','tiago@hotmail.com','"+ GuiUtil.IMAGEM_PADRAO+"');",
                "INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,email_pessoa,foto_pessoa) VALUES (4,'ariana','ariana@hotmail.com','"+ GuiUtil.IMAGEM_PADRAO+"');",
                "INSERT INTO tabela_pessoa (_id_pessoa,nome_pessoa,email_pessoa,foto_pessoa) VALUES (5,'gabriel','gabriel@hotmail.com','"+ GuiUtil.IMAGEM_PADRAO+"');"};

        for (String pessoa : pessoas) {
            db.execSQL(pessoa);
        }
    }
    public static void inserirUsuarios(SQLiteDatabase db) {

        String[] usuarios =
                {"INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (1,'bruna','bruna10',1);",
                "INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (2,'victor','victor10',2);",
                "INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (3,'tiago','tiago10',3);",
                "INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (4,'ariana','ariana10',4);",
                "INSERT INTO `tabela_usuario` (_id_usuario,login_usuario,senha_usuario,id_pessoa_usuario) VALUES (5,'gabriel','gabriel10',5);",};

        for (String usuario : usuarios) {
            db.execSQL(usuario);
        }
    }

    public static void inserirEventos(SQLiteDatabase db){
        String[] eventos =
                {"INSERT INTO `tabela_evento` (_id_evento,nome_evento,descricao_evento,hora_inicio_evento,hora_fim_evento,data_inicio_evento," +
                        "data_fim_evento,nivel_prioridade_enum,'pessoa_criadora_id') VALUES (1,'evento1','descricao1','02:00:00','03:00:00','07-06-2016','08-06-2016','VERDE',1);"};
        for (String evento : eventos){
            db.execSQL(evento);
        }
    }
}