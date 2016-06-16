package br.com.mindbit.controleacesso.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOME_DB = "banco";
    private static final int VERSAO_DB = 2;

    public DatabaseHelper (Context context) {
        super(context.getApplicationContext(),NOME_DB,null,VERSAO_DB);
    }

    //TABELA PESSOA
    public static final String TABELA_PESSOA= "tabela_pessoa";
    public static final String PESSOA_ID = "_id_pessoa";
    public static final String PESSOA_NOME = "nome_pessoa";
    public static final String PESSOA_EMAIL = "email_pessoa";
    public static final String PESSOA_FOTO = "foto_pessoa";

    //TABELA USUARIO
    public static final String TABELA_USUARIO = "tabela_usuario";
    public static final String USUARIO_ID = "_id_usuario";
    public static final String USUARIO_LOGIN = "login_usuario";
    public static final String USUARIO_SENHA = "senha_usuario";
    public static final String USUARIO_PESSOA_ID = "id_pessoa_usuario";

    //TABELA EVENTO
    public static final String TABELA_EVENTO = "tabela_evento";
    public static final String EVENTO_ID = "_id_evento";
    public static final String EVENTO_NOME = "nome_evento";
    public static final String EVENTO_DESCRICAO = "descricao_evento";
    public static final String EVENTO_DATA_INICIO = "data_inicio_evento";
    public static final String EVENTO_DATA_FIM = "data_fim_evento";
    public static final String EVENTO_NIVEL_PRIORIDADE_ENUM = "nivel_prioridade_enum";
    public static final String PESSOA_CRIADORA_ID = "id_pessoa_criadora";

    //TABELA DISCIPLINA
    public static final String TABELA_DISCIPLINA = "tabela_disciplina";
    public static final String DISCIPLINA_ID = "_id_disciplina";
    public static final String DISCIPLINA_NOME = "nome_disciplina";
    public static final String DISCIPLINA_CODIGO = "codigo_disciplina";
    public static final String PESSOA_CRIADORA_DISCIPLINA_ID = "id_pessoa_criadora_disciplina";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptTableSQL.getTabelaPessoa());
        db.execSQL(ScriptTableSQL.getTabelaUsuario());
        db.execSQL(ScriptTableSQL.getTabelaEvento());
        db.execSQL(ScriptTableSQL.getTabelaDisciplina());

        PopularTabela.inserirUsuarios(db);
        PopularTabela.inserirPessoas(db);
        PopularTabela.inserirEventos(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABELA_USUARIO);
        db.execSQL("drop table if exists " + TABELA_PESSOA);
        db.execSQL("drop table if exists " + TABELA_EVENTO);
        db.execSQL("drop table if exists " + TABELA_DISCIPLINA);
        onCreate(db);
    }

}