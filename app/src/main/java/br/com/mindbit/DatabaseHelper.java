package br.com.mindbit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bruna on 14/05/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOME_DB = "banco";
    private static final int VERSAO_DB = 1;

    public DatabaseHelper (Context context) {
        super(context.getApplicationContext(),NOME_DB,null,VERSAO_DB);
    }

    //TABELA PESSOA

    public static final String TABELA_PESSOA= "tabela_pessoa";
    public static final String PESSOA_ID = "_id_pessoa";
    public static final String PESSOA_NOME = "nome_pessoa";
    public static final String PESSOA_CPF = "cpf_pessoa";
    public static final String PESSOA_FOTO = "foto_pessoa";

    //TABELA USUARIO

    public static final String TABELA_USUARIO = "tabela_usuario";
    public static final String USUARIO_ID = "_id_usuario";
    public static final String USUARIO_LOGIN = "login_usuario";
    public static final String USUARIO_SENHA = "senha_usuario";
    public static final String USUARIO_PESSOA_ID = "id_pessoa_usuario";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptTableSQL.getTabelaPessoa());

        db.execSQL(ScriptTableSQL.getTabelaUsuario());

        ScriptPopularTableSQL.inserirUsuariosDB(db);

        ScriptPopularTableSQL.inserirPessoasDB(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABELA_USUARIO);
        db.execSQL("drop table if exists " + TABELA_PESSOA);
        onCreate(db);
    }

}