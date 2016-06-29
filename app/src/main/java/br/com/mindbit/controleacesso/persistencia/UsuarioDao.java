package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.infra.gui.MindbitException;

public class UsuarioDao {

    private static DatabaseHelper databaseHelper;
    private static EventoDao eventoDao;
    private static Context contexto;

    private static UsuarioDao  instanciaUsuarioDao = new UsuarioDao();
    private UsuarioDao(){};

    /* singleton */
    public static UsuarioDao getInstancia(Context contexto){
        UsuarioDao.contexto = contexto;
        UsuarioDao.databaseHelper = new DatabaseHelper(contexto);
        UsuarioDao.eventoDao = EventoDao.getInstancia(contexto);
        return instanciaUsuarioDao;
    }


    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    /**
     * @param pessoa pessoa a ser registrada no sistema
     */
    public void cadastrarPessoa(Pessoa pessoa){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.PESSOA_NOME, pessoa.getNome());
        values.put(DatabaseHelper.PESSOA_EMAIL, pessoa.getEmail());
        values.put(DatabaseHelper.PESSOA_FOTO, pessoa.getFoto().toString());
        //String.valueOf?? To com sono
        //values.put(DatabaseHelper.PESSOA_AMIGO, String.valueOf(pessoa.getAmigos()));

        long foreing_key_id_pessoa = db.insert(DatabaseHelper.TABELA_PESSOA, null, values);

        values = new ContentValues();
        values.put(DatabaseHelper.USUARIO_LOGIN, pessoa.getUsuario().getLogin());
        values.put(DatabaseHelper.USUARIO_PESSOA_ID, foreing_key_id_pessoa);
        values.put(DatabaseHelper.USUARIO_SENHA, pessoa.getUsuario().getSenha());

        db.insert(DatabaseHelper.TABELA_USUARIO, null, values);
        db.close();
    }

    /**
     * @param cursor cursor a ser usado na criacao da pessoa
     * @return objeto pessoa preenchido
     */
    private Pessoa criarPessoa(Cursor cursor){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(cursor.getInt(0));
        pessoa.setNome(cursor.getString(1));
        pessoa.setEmail(cursor.getString(2));
        pessoa.setFoto(Uri.parse(cursor.getString(3)));
        //pessoa.setAmigos(cursor.getString(4));
        return pessoa;
    }

    /**
     * @param cursor cursor a ser usado na criacao do usuario
     * @return objeto usuario preenchido
     */
    private Usuario criarUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(0));
        usuario.setLogin(cursor.getString(1));
        usuario.setSenha(cursor.getString(2));
        return usuario;
    }

    /**
     * @param login login do usuario a ser buscado
     * @param senha senha do usuario a ser buscado
     * @return usuario encontrado atraves dos parametros informados
     */
    public Usuario buscarUsuario(String login, String senha){
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Usuario usuario = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABELA_USUARIO+
                " WHERE "+DatabaseHelper.USUARIO_LOGIN+" =? AND "+DatabaseHelper.USUARIO_SENHA+" =?", new String[]{login, senha});
        if (cursor.moveToFirst()){
            usuario = criarUsuario(cursor);
        }
        db.close();
        cursor.close();
        return usuario;
    }

    /**
     * @param id id da pessoa a ser buscada
     * @return pessoa encontrada atraves do parametro informado
     */
    public Pessoa buscarPessoaId(int id){
        Pessoa pessoa = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+databaseHelper.TABELA_PESSOA+" WHERE "+
                        databaseHelper.PESSOA_ID+" =?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            pessoa = criarPessoa(cursor);
        }
        db.close();
        cursor.close();

        return pessoa;
    }

    /**
     * @param login login do usuario a ser encontrado
     * @return usuario encontrado atraves do parametro informado
     */
    public Usuario buscarUsuarioLogin(String login){
        SQLiteDatabase db;
        Usuario usuario = null;

        db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_USUARIO +
                " WHERE " + DatabaseHelper.USUARIO_LOGIN + " =?", new String[]{login});
        if (cursor.moveToFirst()){
            usuario = criarUsuario(cursor);
        }
        db.close();
        cursor.close();
        return usuario;

    }
    /**
     * @param email email da pessoa a ser encontrada
     * @return pessoa encontrada atraves do parametro informado
     * @throws MindbitException
     */
    public Pessoa buscaPessoaPorEmail(String email) throws MindbitException {
        Pessoa pessoa = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_PESSOA +
                " WHERE " + DatabaseHelper.PESSOA_EMAIL + " = ? ", new String[]{email});
        if (cursor.moveToFirst()) {
            pessoa = criarPessoa(cursor);
        }
        db.close();
        cursor.close();
        return pessoa;
    }


}
