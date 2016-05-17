package br.com.mindbit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import br.com.mindbit.dominio.Pessoa;
import br.com.mindbit.dominio.Usuario;

public class UsuarioDao {

    private static DatabaseHelper databaseHelper;
    private static Context contexto;

    private static UsuarioDao  instanciaUsuarioDao = new UsuarioDao();
    private UsuarioDao(){};

    public static UsuarioDao getInstancia(Context contexto){
        UsuarioDao.contexto = contexto;
        UsuarioDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaUsuarioDao;
    }

    private SQLiteDatabase db;
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    public void cadastrarUsuario(Pessoa pessoa){
        db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.PESSOA_NOME, pessoa.getNome());
        values.put(DatabaseHelper.PESSOA_CPF, pessoa.getCpf());
        values.put(DatabaseHelper.PESSOA_EMAIL, pessoa.getEmail());
        values.put(DatabaseHelper.PESSOA_FOTO, pessoa.getFoto().toString());

        long foreing_key_id_pessoa = db.insert(DatabaseHelper.TABELA_PESSOA, null, values);

        values = new ContentValues();
        values.put(DatabaseHelper.USUARIO_ID, pessoa.getUsuario().getId());
        values.put(DatabaseHelper.USUARIO_LOGIN, pessoa.getUsuario().getLogin());
        values.put(DatabaseHelper.USUARIO_SENHA, pessoa.getUsuario().getSenha());

        db.insert(DatabaseHelper.TABELA_USUARIO, null, values);
        db.close();
    }

    private Pessoa criarPessoa(Cursor cursor){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(cursor.getInt(0));
        pessoa.setNome(cursor.getString(1));
        pessoa.setCpf(cursor.getString(2));
        pessoa.setEmail(cursor.getString(3));
        pessoa.setFoto(Uri.parse(cursor.getString(4)));

        return pessoa;
    }

    private Usuario criarUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(0));
        usuario.setLogin(cursor.getString(1));
        usuario.setSenha(cursor.getString(2));

        return usuario;
    }

    public Usuario buscarLogin(String login){
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Usuario usuario = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABELA_USUARIO+
                " WHERE "+DatabaseHelper.USUARIO_LOGIN+" =?", new String[]{login});
        if (cursor.moveToFirst()){
            usuario = criarUsuario(cursor);
        }
        db.close();
        cursor.close();

        return usuario;
    }

}
