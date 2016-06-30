package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.MindbitException;

public class AmigoDao {
    private static DatabaseHelper databaseHelper;
    private static AmigoDao instanciaAmigoDao = new AmigoDao();

    private AmigoDao(){}

    public static AmigoDao getInstancia(Context contexto) {
        AmigoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaAmigoDao;
    }

    private Amigo criarAmigo(Cursor cursor){
        Amigo amigo = new Amigo();
        amigo.setId(cursor.getInt(0));
        amigo.setNome(cursor.getString(1));
        amigo.setEmail(cursor.getString(2));
        return amigo;
    }

    public void addAmigo(Amigo amigo){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.AMIGO_NOME, amigo.getNome());
        values.put(DatabaseHelper.AMIGO_EMAIL, amigo.getEmail());
        int idPessoaUsuario = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.ID_PESSOA_USUARIO, idPessoaUsuario);

        db.insert(DatabaseHelper.TABELA_AMIGO, null, values);
        db.close();
    }

    public Amigo buscarAmigoPorEmail(String email) throws MindbitException {
        Amigo amigo = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_AMIGO +
                " WHERE " + DatabaseHelper.AMIGO_EMAIL + " = ? ", new String[]{email});
        if (cursor.moveToFirst()) {
            amigo = criarAmigo(cursor);
        }
        db.close();
        cursor.close();
        return amigo;
    }

    public List<Amigo> listarAmigos(int id) throws MindbitException {
        Amigo amigo = null;
        List<Amigo> listaAmigos = new ArrayList<Amigo>();

        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ databaseHelper.TABELA_AMIGO +" WHERE " +
                databaseHelper.ID_PESSOA_USUARIO+ " =?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            amigo = criarAmigo(cursor);
            listaAmigos.add(amigo);
        }

        db.close();
        cursor.close();
        return listaAmigos;
    }
}
