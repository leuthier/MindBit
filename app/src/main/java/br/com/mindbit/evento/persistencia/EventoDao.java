package br.com.mindbit.evento.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Time;

import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.persistencia.DatabaseHelper;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.evento.dominio.Evento;
import br.com.mindbit.evento.dominio.NivelPrioridade;

/**
 * Created by Ariana on 07/06/2016.
 */
public class EventoDao {
    private static DatabaseHelper databaseHelper;
    private static Context contexto;

    private static EventoDao instanciaEventoDao = new EventoDao();

    private EventoDao() {
    }

    public static EventoDao getInstancia(Context contexto) {
        EventoDao.contexto = contexto;
        EventoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaEventoDao;
    }

    private SQLiteDatabase db;
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    public void cadastrarEvento(Evento evento) {
        db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long foreing_key_id_evento = db.insert(DatabaseHelper.TABELA_EVENTO, null, values);

        values.put(DatabaseHelper.EVENTO_ID, foreing_key_id_evento);
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());
        //todos os .toString() podem dar treta em algum lugar
        values.put(DatabaseHelper.EVENTO_DESCRICAO, evento.getDescricao().toString());
        values.put(DatabaseHelper.EVENTO_HORA_INICIO, evento.getHoraInicio().toString());
        values.put(DatabaseHelper.EVENTO_HORA_FIM, evento.getHoraFim().toString());
        values.put(DatabaseHelper.EVENTO_DATA_INICIO, evento.getDataInicio().toString());
        values.put(DatabaseHelper.EVENTO_DATA_FIM, evento.getDataFim().toString());
        //tá correto?
        values.put(DatabaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM, evento.getNivelPrioridadeEnum().toString());

        db.insert(DatabaseHelper.TABELA_EVENTO, null, values);
        db.close();
    }

    public Evento criarEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setId(cursor.getInt(0));
        evento.setNome(cursor.getString(1));
        evento.setDescricao(cursor.getString(2));
        evento.setHoraInicio(Time.valueOf(cursor.getString(3)));
        evento.setHoraFim(Time.valueOf(cursor.getString(4)));
        evento.setDataInicio(Date.valueOf(cursor.getString(5)));
        evento.setDataFim(Date.valueOf(cursor.getString(6)));
        evento.setNivelPrioridadeEnum(NivelPrioridade.valueOf(cursor.getString(7)));
        return evento;
    }

    public Evento buscarEvento(String nome) {
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Evento evento = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_EVENTO +
                " WHERE " + DatabaseHelper.EVENTO_NOME + " =?", new String[]{nome});
        if (cursor.moveToFirst()) {
            evento = criarEvento(cursor);
        }
        db.close();
        cursor.close();
        return evento;
    }

    public Evento buscarEventoId(int id) {
        Evento evento = null;
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " +
                databaseHelper.EVENTO_ID + " =?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            evento = criarEvento(cursor);
        }

        db.close();
        cursor.close();
        return evento;
    }
}